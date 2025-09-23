import config.DBConfig;
import db.DBConnection;
import db.DatabaseInitializer;
import entity.BitcoinWallet;
import entity.Wallet;
import repository.interfaces.Repository;

public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(
                DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD
        );


        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConnection);
        dbInitializer.init();

        Repository<Wallet> WalletRepository = new repository.impl.WalletRepositoryImpl(dbConnection);
        Wallet wallet = new BitcoinWallet(1, "hoho");
        wallet.setPassword("password123");

        WalletRepository.save(wallet);

        WalletRepository.findAll().forEach(w -> System.out.println(w.getPassword()));

    }
}