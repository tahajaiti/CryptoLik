package ui.menu.sub;

import service.impl.WalletDisplayService;
import ui.Menu;
import ui.menu.MenuResult;

public class WalletDisplayMenu implements Menu {
    private static final String ID = "wallet_display_menu";
    private final WalletDisplayService walletDisplayService;

    public WalletDisplayMenu(WalletDisplayService walletDisplayService) {
        this.walletDisplayService = walletDisplayService;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean canRender() {
        return true;
    }

    @Override
    public MenuResult render(ui.UIManager ui) {

        ui.showL(walletDisplayService.getWalletDetails());

        return MenuResult.goTo("main_menu");
    }
}
