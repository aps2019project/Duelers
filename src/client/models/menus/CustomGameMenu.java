package client.models.menus;

import client.Client;

public class CustomGameMenu implements Menu {
    private static final CustomGameMenu CUSTOM_GAME_MENU = new CustomGameMenu();

    private CustomGameMenu() {

    }

    public static CustomGameMenu getInstance() {
        return CUSTOM_GAME_MENU;
    }

    public void startGame(String deckName,String mode,int numberOfFlags) {

    }

    @Override
    public void exit(Client client) {

    }
}
