import db.DatabaseInitializer;
import di.DIContainerImpl;
import di.DependancyRegistery;
import service.impl.WalletDisplayService;
import service.interfaces.AuthService;
import service.interfaces.TransactionService;
import ui.Menu;
import ui.UIManager;
import ui.menu.AuthMenu;
import ui.menu.MainMenu;
import ui.menu.MenuBootloader;
import ui.menu.sub.TxCreateMenu;
import ui.menu.sub.WalletDisplayMenu;

public class Main {
    public static void main(String[] args) {

        DependancyRegistery.boot();

        DatabaseInitializer dbInitializer = DIContainerImpl.resolveStatic(DatabaseInitializer.class);

        dbInitializer.init();

        MainMenu mainMenu = new MainMenu();
        Menu authMenu = new AuthMenu(DIContainerImpl.resolveStatic(AuthService.class));
        Menu walletDisplayMenu = new WalletDisplayMenu(DIContainerImpl.resolveStatic(WalletDisplayService.class));
        Menu txMenu = new TxCreateMenu(DIContainerImpl.resolveStatic(TransactionService.class), DIContainerImpl.resolveStatic(AuthService.class));

        MenuBootloader bootloader = new MenuBootloader(DIContainerImpl.resolveStatic(UIManager.class));
        bootloader.register(mainMenu);
        bootloader.register(authMenu);
        bootloader.register(walletDisplayMenu);
        bootloader.register(txMenu);
        bootloader.boot(authMenu.getId());

    }
}