import db.DatabaseInitializer;
import di.DIContainerImpl;
import di.DependancyRegistery;
import service.interfaces.MempoolService;
import ui.UIManager;
import ui.menu.MenuBootloader;

public class Main {
    public static void main(String[] args) {

        DependancyRegistery.boot();

        DatabaseInitializer dbInitializer = DIContainerImpl.resolveStatic(DatabaseInitializer.class);
        dbInitializer.init();

        MempoolService mempoolService = DIContainerImpl.resolveStatic(MempoolService.class);
        Thread mempoolThread = new Thread(mempoolService);
        mempoolThread.start();


        MenuBootloader bootloader = new MenuBootloader(
                DIContainerImpl.resolveStatic(UIManager.class),
                DIContainerImpl.getInstance()
        );
        bootloader.boot("auth_menu");

    }
}