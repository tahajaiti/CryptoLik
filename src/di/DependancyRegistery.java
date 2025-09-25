package di;

import config.DBConfig;
import db.DBConnection;
import db.DatabaseInitializer;
import mapper.dto.impl.WalletDtoMapper;
import mapper.dto.interfaces.WalletDTOMapper;
import repository.impl.WalletRepositoryImpl;
import repository.interfaces.WalletRepository;
import service.impl.AuthServiceImpl;
import service.impl.SessionServiceImpl;
import service.interfaces.AuthService;
import ui.UIManager;
import ui.impl.ConsoleUIManager;

public class DependancyRegistery {
    
    

    public static void boot(){
        DIContainer container = DIContainerImpl.getInstance();

        DBConnection dbConnection = new DBConnection(
                DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD
        );
        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConnection);

        
        container.register(DBConnection.class, dbConnection);
        container.register(DatabaseInitializer.class, dbInitializer);
        container.register(ConsoleUIManager.class);
        container.register(UIManager.class, container.resolve(ConsoleUIManager.class));

        container.register(WalletRepositoryImpl.class);
        container.register(WalletRepository.class, container.resolve(WalletRepositoryImpl.class));

        container.register(WalletDtoMapper.class);
        container.register(WalletDTOMapper.class, container.resolve(WalletDtoMapper.class));
        container.register(SessionServiceImpl.class);
        container.register(AuthServiceImpl.class);
        
        container.register(AuthService.class, container.resolve(AuthServiceImpl.class));

    }
}
