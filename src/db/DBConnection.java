package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import exceptions.DatabaseConnectionException;
import util.Log;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = Objects.requireNonNull(url, "Database URL cannot be null");
        this.user = Objects.requireNonNull(user, "Database user cannot be null");
        this.password = Objects.requireNonNull(password, "Database password cannot be null");

        Log.info(getClass(), "DBConnection initialized with URL: " + url + ", User: " + user);
    }

    public Connection getConnection()  {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn.isClosed() || !conn.isValid(5)) {
                Log.warn(getClass(), "Failed to establish a valid database connection.");
                throw new SQLException("Invalid database connection");
            }
            return conn;

        } catch (Exception e) {
            Log.error(getClass(), "Failed to establish database connection", e);
            throw new DatabaseConnectionException("Failed to establish database connection", e);
        }
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2);
        } catch (Exception e) {
            Log.error(getClass(), "Database connection test failed", e);
            return false;
        }
    }
}
