package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.doSql("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    LOG.warning("ERROR: a resume with a similar uuid is not present in the storage; uuid: " + r.getUuid());
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContactDB(r.getUuid());
            insertContactDB(r, conn);

            deleteSectionDB(r.getUuid());
            insertSectionDB(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContactDB(r, conn);
            insertSectionDB(r, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        Resume resume =
                sqlHelper.doSql(
                        "SELECT * FROM resume r " +
                                "LEFT JOIN contact c " +
                                "ON r.uuid = c.resume_uuid " +
                                "WHERE r.uuid=?",
                        ps -> {
                            ps.setString(1, uuid);
                            ResultSet rs = ps.executeQuery();
                            if (!rs.next()) {
                                LOG.warning("ERROR: a resume with a similar uuid is not present in the storage; uuid: " + uuid);
                                throw new NotExistStorageException(uuid);
                            }
                            Resume r = new Resume(uuid, rs.getString("full_name"));
                            do {
                                addContact(r, rs);
                            } while (rs.next());
                            return r;
                        });

        sqlHelper.doSql("SELECT * FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(resume, rs);
            }
            return null;
        });
        return resume;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        sqlHelper.doSql("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = sqlHelper.doSql("" +
                "SELECT * FROM resume r " +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet res = ps.executeQuery();
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            while (res.next()) {
                String uuid = res.getString("uuid");
                resumeMap.put(uuid, new Resume(uuid, res.getString("full_name")));
            }
            return resumeMap;
        });

        sqlHelper.doSql("SELECT * FROM contact", ps -> {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String uuid = res.getString("resume_uuid");
                addContact(map.get(uuid), res);
            }
            return null;
        });

        sqlHelper.doSql("SELECT * FROM section", ps -> {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String uuid = res.getString("resume_uuid");
                addSection(map.get(uuid), res);
            }
            return null;
        });
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return sqlHelper.doSql("SELECT COUNT(*) AS count FROM resume", ps -> {
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("count");
        });
    }

    private void deleteContactDB(String uuid) {
        sqlHelper.doSql("DELETE FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private void deleteSectionDB(String uuid) {
        sqlHelper.doSql("DELETE FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private static void insertContactDB(Resume r, Connection c) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContactMap().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void insertSectionDB(Resume r, Connection c) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSectionMap().entrySet()) {
                if (e.getKey() == SectionType.EXPERIENCE || e.getKey() == SectionType.EDUCATION) {
                    continue;
                }
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, getSectionDescription(e.getValue(), e.getKey()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static String getSectionDescription(Section section, SectionType type) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection textSection = (TextSection) section;
                return textSection.getText();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection listSection = (ListSection) section;
                Iterator<String> iterator = listSection.getList().iterator();
                StringBuilder s = new StringBuilder();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                if (iterator.hasNext()) {
                        s.append(next).append("/;/");
                    } else {
                        s.append(next);
                    }
                }
                return s.toString();
        }
        return null;
    }

    private void addContact(Resume r, ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            r.addContactInfo(ContactType.valueOf(type),
                    resultSet.getString("value"));
        }
    }

    private void addSection(Resume r, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        SectionType sectionType = SectionType.valueOf(type);
        String value = rs.getString("value");
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                r.addInfoAtSection(sectionType, value);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                r.addInfoAtSection(sectionType, value.split("/;/"));
                break;
        }

    }
}
