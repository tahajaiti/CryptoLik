package ui.menu.sub;

import dto.response.WalletResponseDTO;
import service.interfaces.AuthService;
import ui.Menu;
import ui.menu.MenuResult;

public class FundsMenu implements Menu {
    private static final String ID = "recieve_funds_menu";
    private final AuthService authService;

    public FundsMenu(AuthService authService) {
        this.authService = authService;
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
        WalletResponseDTO wallet = authService.getCurrentUser();

        double amount = ui.getDouble("Enter amount to receive: ");
        ui.showL("You will recieve: " + amount + " " + wallet.getWalletType().name());

        return MenuResult.goTo("main_menu");
    }
}
