package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doSql(String sql, SqlActivity<T> activity) {
        try (Connection c = connectionFactory.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            return activity.action(preparedStatement);
        } catch (SQLException e) {
            if (e.getErrorCode() == 0) throw new ExistStorageException(sql);
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SqlActivity<T> {
        T action(PreparedStatement ps) throws SQLException;
    }

}
