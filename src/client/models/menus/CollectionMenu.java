package client.models.menus;

import client.models.account.Collection;
import client.models.card.Card;
import client.models.card.Deck;

public class CollectionMenu extends Menu {
    private static CollectionMenu collectionMenu;
    private Collection collection;

    private CollectionMenu() {

    }

    public static CollectionMenu getInstance() {
        if (collectionMenu == null)
            collectionMenu = new CollectionMenu();
        return collectionMenu;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void newDeck(String deckName) {

    }

    public void addCardToDeck(Deck deck, Card card) {

    }

    public void removeCardFromDeck(Deck deck, Card card) {

    }

    public void save() {

    }

    public void removeDeck(String deckName) {

    }

    public boolean validateDeck(String deckName) {
        return false;
    }

    public void selectDeck(String DeckName) {

    }
}
