package controller;

import models.Constants;
import models.card.DeckInfo;
import models.message.DataName;
import models.message.Message;

public class StoryMenuController {
    private static StoryMenuController ourInstance;
    private DeckInfo[] stories = null;

    public static StoryMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new StoryMenuController();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeGetDataMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, DataName.STORIES, 0));
        }
        return ourInstance;
    }

    private StoryMenuController() {
    }

    public void startGame(int stage) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeNewStoryGameMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, stage, 0
                )
        );
    }

    synchronized void setStories(DeckInfo[] stories) {
        this.stories = stories;
        this.notify();
    }

    public DeckInfo[] getStories() {
        return stories;
    }
}
