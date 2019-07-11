package controller;

import models.Constants;
import models.account.Collection;
import models.card.Card;
import models.message.DataName;
import models.message.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShopController {
    private static ShopController ourInstance;
    private Collection originalCards;
    private Collection showingCards;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (ourInstance == null) {
            ourInstance = new ShopController();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeGetDataMessage(Constants.SERVER_NAME, DataName.ORIGINAL_CARDS));
        }
        return ourInstance;
    }

    static boolean isLoaded() {
        return ourInstance != null;
    }

    public void buy(String cardName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeBuyCardMessage(Constants.SERVER_NAME, cardName.replaceAll(" ", "")));
    }

    public void sell(String cardName) {
        Card card = Client.getInstance().getAccount().getCollection().findLast(cardName);

        if (card == null) {
            Client.getInstance().getCurrentShow().showError("You don't have such card");
            return;
        }
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeSellCardMessage(Constants.SERVER_NAME, card.getCardId()));
    }

    public Collection getShowingCards() {
        return showingCards;
    }

    public void searchInShop(String cardName) {
        Collection result = originalCards.searchCollection(cardName);
        support.firePropertyChange("search_result", showingCards, result);
        showingCards = result;
    }

    synchronized void setOriginalCards(Collection originalCards) {
        Collection old = this.originalCards;
        this.originalCards = originalCards;
        this.showingCards = originalCards;
        this.notify();
        ShopAdminController.getInstance().setOriginalCards(old, originalCards);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    synchronized void addCard(Card customCard) {
        originalCards.addCard(customCard);
        showingCards.addCard(customCard);
        support.firePropertyChange("search_result", showingCards, originalCards);
        System.out.println(originalCards.getHeroes().size());
        this.notify();
        ShopAdminController.getInstance().addCard(customCard);
    }

    public Collection getOriginalCards() {
        return originalCards;
    }
}
