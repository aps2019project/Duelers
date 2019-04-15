package models.menus;

public class MainMenu extends Menu {
    private static MainMenu MAIN_MENU;

    private MainMenu() {

    }

    public static MainMenu getInstance()
    {
        if (MAIN_MENU == null) {
             MAIN_MENU = new MainMenu();
        }
        return MAIN_MENU;
    }

    public void moveToMenu(String menuName) {

    }
}
