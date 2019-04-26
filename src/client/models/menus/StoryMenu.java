package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;
import client.models.card.DeckInfo;

public class StoryMenu extends Menu {
    private static StoryMenu STORY_MENU;
    private DeckInfo[] stories;

    private StoryMenu() {
    }

    public static StoryMenu getInstance(Client client, String serverName) {
        if (STORY_MENU == null) {
            STORY_MENU = new StoryMenu();
            client.addToSendingMessages(
                    Message.makeGetStoriesInfoMessage(
                            client.getClientName(), serverName, 0
                    )
            );
            client.sendMessages();
        }
        return STORY_MENU;
    }

    public static StoryMenu getInstance() {
        return STORY_MENU;
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

    public void setStories(DeckInfo[] stories) {
        this.stories = stories;
    }

    @Override
    public void showHelp() {
        String help = "Story:" +
                "\n1. " + stories[0].getDeckName() + " - Hero name: " + stories[0].getHeroName() + " - Game mode: " + stories[0].getType() +
                "\n2. " + stories[1].getDeckName() + " - Hero name: " + stories[1].getHeroName() + " - Game mode: " + stories[1].getType() +
                "\n3. " + stories[2].getDeckName() + " - Hero name: " + stories[2].getHeroName() + " - Game mode: " + stories[2].getType() +
                "\n\"start game #number\"";
        View.getInstance().showHelp(help);
    }

    @Override
    public void exit(Client client) {

    }
}
