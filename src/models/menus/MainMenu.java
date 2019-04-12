package models.menus;

public class MainMenu extends Menu {
    private static final MainMenu MAIN_MENU = new MainMenu();

    private MainMenu() {

    }

    private static MainMenu getInstance() {
        return MAIN_MENU;
    }

    public void moveToMenu(String menuName) {

    }
}
