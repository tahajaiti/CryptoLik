package ui.menu;

import ui.Menu;

public class MainMenu implements Menu {

    private static final String ID = "main_menu";

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
        ui.showL("Main Menu:");
        ui.showL("1. View wallet details");
        ui.showL("2. Create a transaction");
        ui.showL("3. Check transaction position in mempool");
        ui.showL("4. Compare fee levels");
        ui.showL("5. Check mempool state");
        ui.showL("6. Recieve funds");
        ui.showL("7. Exit");
        
        return input(ui);
    }

    private MenuResult input(ui.UIManager ui) {
        String choice = ui.getString("> ").trim();

        switch (choice) {
            case "1":
            ui.showL("You selected View wallet");
            return MenuResult.goTo("wallet_display_menu");
            case "2":
            ui.showL("You selected Create a transaction");
            return MenuResult.goTo("tx_create_menu");
            case "3":
            ui.showL("You selected Check transaction position in mempool");
            return MenuResult.stay();
            case "4":
            ui.showL("You selected Compare fee levels");
            return MenuResult.stay();
            case "5":
            ui.showL("You selected Check mempool state");
            return MenuResult.stay();
            case "6":
                ui.showL("You selected Recieve funds");
                return MenuResult.goTo("recieve_funds_menu");
            case "7":
                return MenuResult.exit();
            default:
                ui.showL("Invalid choice, please try again.");
                return MenuResult.stay();
        }
    }

}
