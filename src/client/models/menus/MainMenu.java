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

    public String getHelp() {
        return "\"Enter Collection\"\n" +
                "\"Enter Battle\"\n" +
                "\"Enter Shop\"\n" +
                "\"logout\"\n" +
                "\"save\"\n" +
                "\"exit\"";
    }

    public void moveToMenu(Client client, String menuName) {
        if (menuName.equals("Collection"))
            client.setCurrentMenu(CollectionMenu.getInstance());
        if (menuName.equals("Shop"))
            client.setCurrentMenu(Shop.getInstance());
        if (menuName.equals("Battle"))
            client.setCurrentMenu(BattleMenu.getInstance());
    }

    public void showHelp() {
        View.getInstance().printMainMenuHelp();
    }

    public void save() {
        //TODO
    }

    public void logout() {
        //TODO
    }
}
