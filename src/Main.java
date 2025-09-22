import db.DBConnection;
import db.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/cryptolik",
                "postgres", "root"
        );

        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConnection);
        dbInitializer.init();

    }
}