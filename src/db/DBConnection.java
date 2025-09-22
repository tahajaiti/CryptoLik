package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            Class.forName("org.postgresql.Driver");
            logger.info("PostgreSQL driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "PostgreSQL Driver not found!", e);
            throw new RuntimeException("PostgreSQL Driver not found!", e);
        }

        logger.info("DBConnection created with URL: " + url + ", User: " + user);
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn == null || conn.isClosed()){
                logger.warning("Failed to establish a valid database connection.");
                throw new SQLException("Failed to establish a valid connection");
            }

            logger.info("Database connection established successfully.");
            return conn;
        } catch (SQLException e) {
            logger.severe("Failed to get database connection: " + e.getMessage());
            throw new RuntimeException("Cannot connect to DB", e);
        }
    }
}
