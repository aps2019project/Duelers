package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;
import client.view.request.ExitCommand;
import client.view.request.InputException;

public class MainMenu extends Menu {
    private static MainMenu ourInstance;

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        if (ourInstance == null) {
            ourInstance = new MainMenu();
        }
        return ourInstance;
    }

    public void moveToMenu(Client client, String serverName, String menuName) throws InputException {
        switch (menuName) {
            case "collection":
                client.setCurrentMenu(CollectionMenu.getInstance());
                break;
            case "shop":
                client.setCurrentMenu(Shop.getInstance(client, serverName));
                break;
            case "battle":
                client.setCurrentMenu(BattleMenu.getInstance());
                break;
            default:
                throw new InputException("invalid command");
        }
    }

    @Override
    public void showHelp() {
        String help = "Main:\n" +
                "\"Enter Collection\"\n" +
                "\"Enter Battle\"\n" +
                "\"Enter Shop\"\n" +
                "\"Show Account\"\n" +
                "\"logout\"\n" +
                "\"save\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
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

    public void showAccount(Client client) {
        View.getInstance().showAccount(client.getAccount());
    }
}
