package client.models.menus;

import client.Client;

public class gameCommands implements Menu {
    private static gameCommands ourInstance = new gameCommands();

    private gameCommands() {
    }

    public static gameCommands getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {

    }
}
