package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

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
            deleteContactDB(r.getUuid(), conn);
            insertContactDB(r, conn);

            deleteSectionDB(r.getUuid(), conn);
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
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(r, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(r, rs);
                }
            }
            return r;
        });
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
        Map<String, Resume> map = sqlHelper.doSql("SELECT * FROM resume r ORDER BY uuid, full_name", ps -> {
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

    private void deleteContactDB(String uuid, Connection c) throws SQLException {
        deleteAttribute(uuid, c, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteSectionDB(String uuid, Connection c) throws SQLException {
        deleteAttribute(uuid, c, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void deleteAttribute(String uuid, Connection c, String sql) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
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
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSectionMap().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, JsonParser.write(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume r, ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            r.addContactInfo(ContactType.valueOf(type),
                    resultSet.getString("value"));
        }
    }

    private void addSection(Resume r, ResultSet rs) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
            r.getSectionMap().put(sectionType, JsonParser.read(content, Section.class));
        }
    }
}
