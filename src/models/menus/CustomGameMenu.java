package models.menus;

public class CustomGameMenu extends Menu {
    private static final CustomGameMenu CUSTOM_GAME_MENU = new CustomGameMenu();

    private CustomGameMenu() {

    }

    public static CustomGameMenu getInstance() {
        return CUSTOM_GAME_MENU;
    }

    public void startGame(String gameDetails) {

    }
}
