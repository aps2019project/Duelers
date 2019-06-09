package controller;

import models.Constants;
import models.message.Message;

public class CollectionMenuController {
    private static CollectionMenuController ourInstance;

    private CollectionMenuController() {
    }

    public static CollectionMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new CollectionMenuController();
        }
        return ourInstance;
    }

    public void addCardToDeck(String deckName, String cardID) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeAddCardToDeckMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, deckName, cardID, 0));
    }

    public void removeCardFromDeck(String deckName, String cardID) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeRemoveCardFromDeckMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, deckName, cardID, 0));
    }

    public void removeDeck(String deckName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeRemoveDeckMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, deckName, 0));
    }

    public void selectDeck(String deckName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeSelectDeckMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, deckName, 0));
    }



}
