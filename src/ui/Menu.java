package ui;

import ui.menu.MenuResult;

public interface Menu {

    /**
     * Unique id to be used by the bootloader
     * @returns unique id
     */

    String getId();


    /**
     * Method to render the menu
     * this method blocks until the next action is decided(stay, goto, exit)
     * @param ui UIManager to be used by the menu
     * @return MenuResult with the result of the menu
     */
    MenuResult render(UIManager ui);


    /**
     * Method to check if the menu can be rendered
     * @return true if the menu can be rendered, false otherwise
     */
    boolean canRender();
}
