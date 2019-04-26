package client.models.menus;

import client.Client;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;
import client.models.card.DeckInfo;

public class StoryMenu extends Menu {
    private static StoryMenu STORY_MENU;
    private DeckInfo[] stories;

    private StoryMenu(Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeGetStoriesInfoMessage(
                        client.getClientName(), serverName, 0
                )
        );
        client.sendMessages();
    }

    public static StoryMenu getInstance(Client client, String serverName) {
        if (STORY_MENU == null) {
            STORY_MENU = new StoryMenu(client, serverName);
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

    public DeckInfo[] getStories() {
        return stories;
    }

    public void setStories(DeckInfo[] stories) {
        this.stories = stories;
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
