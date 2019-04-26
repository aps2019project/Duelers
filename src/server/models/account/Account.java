package server.models.account;

import server.Server;
import server.models.card.Deck;
import server.models.card.TempDeck;

import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private Collection collection;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private ArrayList<MatchHistory> matchHistories = new ArrayList<>();
    private int money;
    private int wins;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.money = 15000;
        this.collection = new Collection();
        this.wins = 0;
    }

    public Account(TempAccount account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        if (account.getDecks() != null) {
            for (TempDeck deck : account.getDecks()) {
                this.decks.add(new Deck(deck, collection));
            }
        }
        this.mainDeck = getDeck(account.getMainDeckName());
        this.money = account.getMoney();
        this.wins = account.getWins();
    }

    public boolean hasDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName))
                return true;
        }
        return false;
    }

    public void setMainDeck(String deckName) {
        if (!hasDeck(deckName)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        mainDeck = getDeck(deckName);
    }

    public void changeMoney(int change) {
        money += change;
    }

    public Deck getDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public void addDeck(String deckName) {
        if (hasDeck(deckName)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        decks.add(new Deck(deckName));
    }

    public void deleteDeck(String deckName) {
        if (!hasDeck(deckName)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        decks.remove(getDeck(deckName));
    }

    public void buyCard(String cardName, int price, Collection originalCards) {
        if (price > money || !originalCards.hasCard(cardName)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        collection.addCard(cardName, originalCards, username);
        money -= price;
    }

    public void sellCard(String cardId) {
        if (!collection.hasCard(cardId)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        money += collection.getCard(cardId).getPrice();
        collection.removeCard(collection.getCard(cardId));
    }

    public void addCardToDeck(String cardId, String deckName) {
        Deck deck = getDeck(deckName);
        if (deck == null) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        if (!collection.hasCard(cardId)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        deck.addCard(cardId, collection);
    }

    public void removeCardFromDeck(String cardId, String deckName) {
        Deck deck = getDeck(deckName);
        if (deck == null) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        if (!deck.hasCard(cardId)) {
            Server.getInstance().serverPrint("Deck doesn't have Error");
            return;
        }
        deck.removeCard(collection.getCard(cardId));
    }

    public void selectDeck(String deckName) {
        if (!hasDeck(deckName)) {
            Server.getInstance().serverPrint("Error");
            return;
        }
        mainDeck = getDeck(deckName);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection getCollection() {
        return collection;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public ArrayList<MatchHistory> getMatchHistories() {
        return matchHistories;
    }

    public int getMoney() {
        return money;
    }

    public int getWins() {
        return wins;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }
}