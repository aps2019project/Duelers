package client.models.menus;

import client.Client;
import client.view.View;

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
        if (menuName.equals("collection"))
            client.setCurrentMenu(CollectionMenu.getInstance());
        if (menuName.equals("shop"))
            client.setCurrentMenu(Shop.getInstance());
        if (menuName.equals("battle"))
            client.setCurrentMenu(BattleMenu.getInstance());
    }

    public void showHelp() {
        String help ="\"Enter Collection\"\n" +
                "\"Enter Battle\"\n" +
                "\"Enter Shop\"\n" +
                "\"logout\"\n" +
                "\"save\"\n" +
                "\"exit\"";
        View.getInstance().printHelp(help);
    }

    public void save() {
        //TODO
    }

    public void logout() {
        //TODO
    }
}
