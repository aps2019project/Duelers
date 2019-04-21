package client.models.menus;

import client.Client;

public class StoryMenu implements Menu {
    private static StoryMenu ourInstance = new StoryMenu();

    private StoryMenu() {
    }

    public static StoryMenu getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {

    }
}
