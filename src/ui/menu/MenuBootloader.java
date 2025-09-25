package ui.menu;

import java.util.HashMap;
import java.util.Map;

import ui.Menu;
import ui.UIManager;
import util.Log;

public class MenuBootloader {
    private final Map<String, Menu> menus = new HashMap<>();
    private final UIManager ui;

    public MenuBootloader(UIManager ui) {
        this.ui = ui;
    }

    public void register(Menu menu) {
        menus.put(menu.getId(), menu);
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

            MenuResult result = currentMenu.render(ui);

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
                        ui.showL("Uknownk menu id: " + result.getMenuId());
                        break;
                    }

                    currentMenu = next;
                    break;
            }
        }
    }

}
