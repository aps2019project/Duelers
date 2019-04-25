package client.models.menus;

import client.Client;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();

    private GameCommands() {
    }

    public static GameCommands getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {

    }

    @Override
    public void showHelp() {

    }
}
