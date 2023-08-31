package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.doSql("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement ps = connection.prepareStatement("TRUNCATE resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void update(Resume r) {
        sqlHelper.doSql("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int i = ps.executeUpdate();
            if (i == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doSql("INSERT INTO resume VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            int i = ps.executeUpdate();
            if (i == 0) {
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement ps = connection.prepareStatement("INSERT INTO resume VALUES (?, ?)")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doSql("SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid=?")) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            return new Resume(uuid, rs.getString("full_name"));
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doSql("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            int i = ps.executeUpdate();
            if (i == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doSql("SELECT * FROM resume ORDER BY full_name", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Resume r = new Resume(res.getString(1), res.getString(2));
                resumes.add(r);
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.doSql("SELECT COUNT(*) AS count FROM resume", ps -> {
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("count");
        });
    }
}
