package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = Objects.requireNonNull(url, "Database URL cannot be null");
        this.user = Objects.requireNonNull(user, "Database user cannot be null");
        this.password = Objects.requireNonNull(password, "Database password cannot be null");

        logger.info("DBConnection initialized with URL: " + url);
    }

    public Connection getConnection()  {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn == null || conn.isClosed()) {
                logger.warning("Failed to establish a valid database connection.");
                throw new SQLException("Invalid database connection");
            }
            logger.fine("Database connection established successfully.");
            return conn;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to establish database connection", e);
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database connection test failed", e);
            return false;
        }
    }
}
