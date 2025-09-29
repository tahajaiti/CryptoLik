import db.DatabaseInitializer;
import di.DIContainerImpl;
import di.DependancyRegistery;
import ui.UIManager;
import ui.menu.MenuBootloader;

public class Main {
    public static void main(String[] args) {

        DependancyRegistery.boot();

        DatabaseInitializer dbInitializer = DIContainerImpl.resolveStatic(DatabaseInitializer.class);

        dbInitializer.init();

        MenuBootloader bootloader = new MenuBootloader(
                DIContainerImpl.resolveStatic(UIManager.class),
                DIContainerImpl.getInstance()
        );
        bootloader.boot("auth_menu");

    }
}