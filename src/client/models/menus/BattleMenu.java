package client.models.menus;

import client.Client;
import client.view.View;

public class BattleMenu extends Menu {
    private static final BattleMenu BATTLE_MENU = new BattleMenu();

    private BattleMenu() {
    }

    public static BattleMenu getInstance() {
        return BATTLE_MENU;
    }

    public void moveToSinglePlayerMenu(Client client) {
        client.setCurrentMenu(SinglePlayerMenu.getInstance());
    }

    public void moveToMultiPlayerMenu(Client client, String serverName) {
        client.updateLeaderBoard(serverName);
        View.getInstance().printUsersList(client);
        client.setCurrentMenu(MultiPlayerMenu.getInstance());
    }

    public void backToMain(Client client){
        client.setCurrentMenu(MainMenu.getInstance());
    }

    public String getHelp() {
        return "\"Single Player\"\n" +
                "\"Multi Player\"\n" +
                "\"exit\"";
    }

    public void showHelp() {
        View.getInstance().printBattleMenuHelp();
    }
}