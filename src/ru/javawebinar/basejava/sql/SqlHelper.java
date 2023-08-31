package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlHelper {

    public void doSqlCode(ConnectionFactory cf, String sql) {
        try (Connection c = cf.getConnection()) {

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
