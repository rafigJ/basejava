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
            throw convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private static StorageException convertException(SQLException e) {
        if (e.getSQLState().equals("23505")) throw new ExistStorageException(null);
        return new StorageException(e);
    }

    @FunctionalInterface
    public interface SqlActivity<T> {
        T action(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }

}
