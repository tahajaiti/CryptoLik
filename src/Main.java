import db.DatabaseInitializer;
import di.DIContainerImpl;
import di.DependancyRegistery;
import service.interfaces.AuthService;
import ui.Menu;
import ui.UIManager;
import ui.menu.AuthMenu;
import ui.menu.MainMenu;
import ui.menu.MenuBootloader;

public class Main {
    public static void main(String[] args) {

        DependancyRegistery.boot();

        DatabaseInitializer dbInitializer = DIContainerImpl.resolveStatic(DatabaseInitializer.class);
        
        dbInitializer.init();        


        MainMenu mainMenu = new MainMenu();
        Menu authMenu = new AuthMenu(DIContainerImpl.resolveStatic(AuthService.class));


        UIManager uiManager = DIContainerImpl.resolveStatic(UIManager.class);

        MenuBootloader bootloader = new MenuBootloader(DIContainerImpl.resolveStatic(UIManager.class));
        bootloader.register(mainMenu);
        bootloader.register(authMenu);
        bootloader.boot(authMenu.getId());

    }
}