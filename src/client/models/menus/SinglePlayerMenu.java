package client.models.menus;

import client.Client;
import client.view.View;

public class SinglePlayerMenu extends Menu {
    private static final SinglePlayerMenu SINGLE_PLAYER_MENU = new SinglePlayerMenu();

    private SinglePlayerMenu() {

    }

    public static SinglePlayerMenu getInstance() {
        return SINGLE_PLAYER_MENU;
    }

    public void moveToStoryMenu(Client client) {

    }

    public void moveToCustomGameMenu(Client client){

    }

    public void returnToMainMenu(Client client){

    }

    public void showHelp(){
        String help = "\"story\"\n" +
                "\"custom game\"\n" +
                "\"exit\"";
        View.getInstance().printHelp(help);
    }
}