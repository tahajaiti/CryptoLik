package di;

import config.DBConfig;
import db.DBConnection;
import db.DatabaseInitializer;
import mapper.dto.impl.TransactionDtoMapper;
import mapper.dto.impl.WalletDtoMapper;
import mapper.dto.interfaces.TransactionDTOMapper;
import mapper.dto.interfaces.WalletDTOMapper;
import repository.impl.TransactionRepositoryImpl;
import repository.impl.WalletRepositoryImpl;
import repository.interfaces.TransactionRepository;
import repository.interfaces.WalletRepository;
import service.impl.AuthServiceImpl;
import service.impl.SessionServiceImpl;
import service.impl.TransactionServiceImpl;
import service.impl.WalletDisplayService;
import service.interfaces.AuthService;
import service.interfaces.SessionService;
import service.interfaces.TransactionService;
import ui.UIManager;
import ui.impl.ConsoleUIManager;

public class DependancyRegistery {

    private DependancyRegistery() {
    }

    public static void boot() {
        DIContainer container = DIContainerImpl.getInstance();

        DBConnection dbConnection = new DBConnection(
                DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConnection);

        // register core DB objects
        container.register(DBConnection.class, dbConnection);
        container.register(DatabaseInitializer.class, dbInitializer);
        // initialize DB schema/data if needed
        dbInitializer.init();

        // UI
        container.register(ConsoleUIManager.class);
        container.register(UIManager.class, container.resolve(ConsoleUIManager.class));

        // repositories
        container.register(WalletRepositoryImpl.class);
        container.register(WalletRepository.class, container.resolve(WalletRepositoryImpl.class));
        container.register(TransactionRepositoryImpl.class);
        container.register(TransactionRepository.class, container.resolve(TransactionRepositoryImpl.class));
        // mappers
        container.register(WalletDtoMapper.class);
        container.register(WalletDTOMapper.class, container.resolve(WalletDtoMapper.class));

        container.register(TransactionDtoMapper.class);
        container.register(TransactionDTOMapper.class, container.resolve(TransactionDtoMapper.class));

        // services
        container.register(SessionServiceImpl.class);
        container.register(SessionService.class, container.resolve(SessionServiceImpl.class));

        container.register(AuthServiceImpl.class);
        container.register(AuthService.class, container.resolve(AuthServiceImpl.class));

        // transaction service: register impl then interface mapping
        container.register(TransactionServiceImpl.class);
        container.register(TransactionService.class, container.resolve(TransactionServiceImpl.class));

        container.register(WalletDisplayService.class);

    }
}
