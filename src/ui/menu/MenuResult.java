package ui.menu;

public final class MenuResult {
    public enum Type {
        STAY, GOTO, EXIT
    }

    private final Type type;
    private final String menuId;

    private MenuResult(Type type, String menuId) {
        this.type = type;
        this.menuId = menuId;
    }

    public static MenuResult stay() {
        return new MenuResult(Type.STAY, null);
    }

    public static MenuResult goTo(String menuId) {
        return new MenuResult(Type.GOTO, menuId);
    }

    public static MenuResult exit() {
        return new MenuResult(Type.EXIT, null);
    }

    public Type getType() {
        return type;
    }

    public String getMenuId() {
        return menuId;
    }

}
