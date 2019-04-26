package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;
import client.view.request.ExitCommand;

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

    public void moveToMenu(Client client, String serverName, String menuName) {
        if (menuName.equals("collection")) {
            client.setCurrentMenu(CollectionMenu.getInstance());
        }
        if (menuName.equals("shop")) {
            client.setCurrentMenu(Shop.getInstance(client, serverName));
        }
        if (menuName.equals("battle")) {
            client.setCurrentMenu(BattleMenu.getInstance());
        }
    }

    @Override
    public void showHelp() {
        String help = "Main:\n" +
                "\"Enter Collection\"\n" +
                "\"Enter Battle\"\n" +
                "\"Enter Shop\"\n" +
                "\"logout\"\n" +
                "\"save\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }

    public void save(Client client, String serverName) {
        client.addToSendingMessages(Message.makeSaveAccountMessage(client.getClientName(), serverName, 0));
        client.sendMessages();
    }

    public void logout(Client client, String serverName) {
        client.addToSendingMessages(Message.makeLogOutMessage(client.getClientName(), serverName, 0));
        client.sendMessages();
        client.setCurrentMenu(AccountMenu.getInstance());
        client.setAccount(null);
    }

    @Override
    public void exit(Client client) {
    }

    public void exit() throws ExitCommand {
        throw new ExitCommand();
    }
}
