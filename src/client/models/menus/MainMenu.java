package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;

public class MainMenu implements Menu {
    private static MainMenu MAIN_MENU;

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        if (MAIN_MENU == null) {
            MAIN_MENU = new MainMenu();
        }
        return MAIN_MENU;
    }


    public void moveToMenu(Client client, String serverName, String menuName) {
        if (menuName.equals("collection")) {
            client.setCurrentMenu(CollectionMenu.getInstance());
        }
        if (menuName.equals("shop")) {
            client.addToSendingMessages(
                    Message.makeGetOriginalCardsMessage(client.getClientName(), serverName, 0)
            );
            client.sendMessages();
            client.setCurrentMenu(Shop.getInstance());
        }
        if (menuName.equals("battle")) {
            client.setCurrentMenu(BattleMenu.getInstance());
        }
    }

    public void showHelp() {
        String help = "\"Enter Collection\"\n" +
                "\"Enter Battle\"\n" +
                "\"Enter Shop\"\n" +
                "\"logout\"\n" +
                "\"save\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }

    public void save() {
        //TODO
    }

    public void logout() {
        //TODO
    }

    @Override
    public void exit(Client client) {

    }
}
