package client.models.menus;

import client.Client;

public class MainMenu extends Menu {
    private static MainMenu MAIN_MENU;

    private MainMenu() {

    }

    public static MainMenu getInstance() {
        if (MAIN_MENU == null) {
            MAIN_MENU = new MainMenu();
        }
        return MAIN_MENU;
    }

    public void moveToMenu(Client client, String menuName) {
        if (menuName.equals("Collection"))
            client.setCurrentMenu(CollectionMenu.getInstance());
        if (menuName.equals("Shop"))
            client.setCurrentMenu(Shop.getInstance());
        if (menuName.equals("Battle"))
            client.setCurrentMenu(BattleMenu.getInstance());
    }
}
