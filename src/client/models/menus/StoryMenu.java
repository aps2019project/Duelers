package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class StoryMenu extends Menu {
    private static StoryMenu ourInstance = new StoryMenu();

    private StoryMenu() {
    }

    public static StoryMenu getInstance() {
        return ourInstance;
    }

    public void startGame(int stage, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeNewStoryGameMessage(
                        client.getClientName(), serverName, stage, 0
                )
        );

        if (client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        client.setCurrentMenu(GameCommands.getInstance());
    }

    @Override
    public void showHelp() {
        String help = "Story:\n" +
                "1. first stage\n" +
                "2. second stage\n" +
                "3. third stage\n" +
                "\"start game #number\"";
        View.getInstance().showHelp(help);
    }

    @Override
    public void exit(Client client) {

    }
}
