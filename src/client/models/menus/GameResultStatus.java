package client.models.menus;

import client.Client;

public class GameResultStatus extends Menu{
    private static GameResultStatus ourInstance = new GameResultStatus();

    private GameResultStatus() {
    }

    public GameResultStatus getInstance() {
        return ourInstance;
    }


    @Override
    public void showHelp() {

    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }
}
