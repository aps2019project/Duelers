package controller;

import models.Constants;
import models.account.Collection;
import models.message.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CollectionMenuController {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static CollectionMenuController ourInstance;
    private Collection allShowingCards;
    private Collection currentShowingCards;

    private CollectionMenuController() {
        allShowingCards = Client.getInstance().getAccount().getCollection().toShowing();
        currentShowingCards = allShowingCards;
    }

    public static CollectionMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new CollectionMenuController();
        }
        return ourInstance;
    }

    public void newDeck(String deckName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeCreateDeckMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, deckName, 0));
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

    public void search(String cardName) {
        Collection result = allShowingCards.searchCollection(cardName);
        support.firePropertyChange("search_result", currentShowingCards, result);
        currentShowingCards = result;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public Collection getCurrentShowingCards() {
        return currentShowingCards;
    }
}
