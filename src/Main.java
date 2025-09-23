import db.DBConnection;
import db.DatabaseInitializer;
import entity.BitcoinWallet;
import entity.Wallet;
import repository.interfaces.Repository;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/cryptolik",
                "postgres", "root"
        );

        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConnection);
        dbInitializer.init();

        Repository<Wallet> WalletRepository = new repository.impl.WalletRepositoryImpl(dbConnection);
        Wallet wallet = new BitcoinWallet(1, "hehehehe");

        WalletRepository.save(wallet);

        WalletRepository.findAll().forEach(System.out::println);

    }
}