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
        return false;
    }


    @Override
    public MenuResult render(ui.UIManager ui) {
        ui.showL("Main Menu:");
        ui.showL("1. Option 1");
        ui.showL("2. Option 2");
        ui.showL("3. Exit");
        String choice = ui.getString("> ").trim();

        if (choice.equals("1")) {
            ui.showL("You selected Option 1");
            return MenuResult.stay();
        } else if (choice.equals("2")) {
            ui.showL("You selected Option 2");
            return MenuResult.stay();
        } else if (choice.equals("3")) {
            return MenuResult.exit();
        } else {
            ui.showL("Invalid choice, please try again.");
            return MenuResult.stay();
        }

    }

}
