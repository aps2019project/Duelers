package models.menus;

import models.account.Collection;
import models.card.Card;
import models.card.Deck;

public class CollectionMenu extends Menu{
    private static final CollectionMenu COLLECTION_MENU = new CollectionMenu();
    private Collection collection;

    private CollectionMenu() {

    }

    public static CollectionMenu getInstance() {
        return COLLECTION_MENU;
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
