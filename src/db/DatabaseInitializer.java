package db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.stream.Stream;

import exceptions.DatabaseInitializationException;
import util.Log;

public class DatabaseInitializer {

    private final DBConnection dbConnection;

    public DatabaseInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void init() {
        Path sqlPath = Paths.get("resources/sql");

        try (Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                Stream<Path> paths = Files.walk(sqlPath)) {

            paths.filter(Files::isRegularFile)
                    .sorted()
                    .forEach(path -> executeSql(stmt, path));

        } catch (Exception e) {
            throw new DatabaseInitializationException(
                    String.format("Failed to initialize database: %s", e.getMessage()), e);
        }
    }

    private void executeSql(Statement stmt, Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            String sql = lines
                    .reduce("", (a, b) -> a + "\n" + b);

            if (!sql.trim().isEmpty()) {
                stmt.execute(sql);
            }

            Log.info(getClass(), "Executed SQL: " + path.getFileName());
        } catch (Exception e) {
            throw new DatabaseInitializationException(
                    "Failed to execute SQL file: " + path.getFileName() + " - " + e.getMessage());
        }
    }

}
