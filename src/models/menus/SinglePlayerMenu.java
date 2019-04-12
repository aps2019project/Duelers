package models.menus;

public class SinglePlayerMenu extends Menu {
    private static final SinglePlayerMenu SINGLE_PLAYER_MENU = new SinglePlayerMenu();

    private SinglePlayerMenu() {

    }

    public static SinglePlayerMenu getInstance() {
        return SINGLE_PLAYER_MENU;
    }

    public void moveToMenu(String menuName) {

    }
}