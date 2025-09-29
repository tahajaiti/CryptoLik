package ui.menu;

import di.DIContainerImpl;
import exceptions.FailedToRegisterMenuException;
import ui.Menu;
import ui.UIManager;
import ui.menu.sub.FundsMenu;
import ui.menu.sub.TxCreateMenu;
import ui.menu.sub.WalletDisplayMenu;
import util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuBootloader {
    private final Map<String, Menu> menus = new HashMap<>();
    private final UIManager ui;
    private final DIContainerImpl diContainer;

    private final List<Class<? extends Menu>> menuClasses = Arrays.asList(
            AuthMenu.class,
            MainMenu.class,
            WalletDisplayMenu.class,
            TxCreateMenu.class,
            FundsMenu.class
    );

    public MenuBootloader(UIManager ui, DIContainerImpl diContainer) {
        this.ui = ui;
        this.diContainer = diContainer;

        registerMenus();
    }

    private void registerMenus() {
        for (Class<? extends Menu> menuClass : menuClasses) {
            try {
                Menu menu = diContainer.resolve(menuClass);
                menus.put(menu.getId(), menu);
            } catch (Exception e) {
                throw new FailedToRegisterMenuException("Failed to register menu: " + menuClass.getName(), e);
            }
        }
    }

    public void boot(String startId) {
        Menu currentMenu = menus.get(startId);
        if (currentMenu == null) {
            throw new IllegalArgumentException("Menu with id " + startId + " not found");
        }

        while (true) {
            if (!currentMenu.canRender()) {
                Log.error(getClass(), "Menu " + currentMenu.getId() + " cannot be rendered, exiting");
                return;
            }

            MenuResult result = null;
            try {
                result = currentMenu.render(ui);
            } catch (Exception e) {
                Log.error(getClass(), "Error: " + e.getMessage());
                result = MenuResult.stay();
            }

            if (result == null) {
                Log.error(getClass(), "Menu " + currentMenu.getId() + " returned null result, exiting",
                        new IllegalStateException("Menu Result cannot be null"));
                return;
            }

            switch (result.getType()) {
                case EXIT:
                    return;
                case STAY:
                    continue;
                case GOTO:
                    Menu next = menus.get(result.getMenuId());
                    if (next == null) {
                        ui.showL("Unknown menu id: " + result.getMenuId());
                        break;
                    }

                    currentMenu = next;
                    break;
            }
        }
    }
}