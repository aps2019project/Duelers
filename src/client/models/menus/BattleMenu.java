package client.models.menus;

import client.Client;
import client.view.View;

public class BattleMenu extends Menu {
    private static BattleMenu ourInstance;

    private BattleMenu() {
    }

    public static BattleMenu getInstance() {
        if (ourInstance == null) {
            ourInstance = new BattleMenu();
        }
        return ourInstance;
    }

    public void moveToSinglePlayerMenu(Client client) {
        client.setCurrentMenu(SinglePlayerMenu.getInstance());
    }

    public void moveToMultiPlayerMenu(Client client, String serverName) {
        client.updateLeaderBoard(serverName);
        View.getInstance().showUsersList(client);
        client.setCurrentMenu(MultiPlayerMenu.getInstance());
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    @Override
    public void showHelp() {
        String help = "Battle:\n" +
                "\"Single Player\"\n" +
                "\"Multi Player\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }
}