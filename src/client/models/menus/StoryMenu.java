package client.models.menus;

import client.Client;
import client.models.card.DeckInfo;
import client.models.message.DataName;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class StoryMenu extends Menu {
    private static StoryMenu ourInstance;
    private DeckInfo[] stories;

    private StoryMenu() {
    }

    public static StoryMenu getInstance(Client client, String serverName) {
        if (ourInstance == null) {
            ourInstance = new StoryMenu();
            client.addToSendingMessages(
                    Message.makeGetDataMessage(client.getClientName(), serverName, DataName.STORIES, 0));
            client.sendMessages();
        }
        return ourInstance;
    }

    public static StoryMenu getInstance() {
        return ourInstance;
    }

    public void startGame(int stage, Client client, String serverName) throws InputException {
        stage--;
        if (stage>=3||stage<0){
            throw new InputException("stage number is not valid");
        }
        client.addToSendingMessages(
                Message.makeNewStoryGameMessage(
                        client.getClientName(), serverName, stage, 0
                )
        );
        client.sendMessages();
        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        client.setCurrentMenu(GameCommands.getInstance());
    }

    public void setStories(DeckInfo[] stories) {
        this.stories = stories;
    }

    @Override
    public void showHelp() {
        String help = "Story:" +
                "\n1. " + stories[0].getDeckName() + " - Hero name: " + stories[0].getHeroName() + " - Game mode: " + stories[0].getType() +
                "\n2. " + stories[1].getDeckName() + " - Hero name: " + stories[1].getHeroName() + " - Game mode: " + stories[1].getType() +
                "\n3. " + stories[2].getDeckName() + " - Hero name: " + stories[2].getHeroName() + " - Game mode: " + stories[2].getType() +
                "\n\"start game #number\"\n\"exit\"";
        View.getInstance().showHelp(help);
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(SinglePlayerMenu.getInstance());
    }
}
