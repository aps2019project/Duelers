package server.models.account;

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
    }

    public Account(TempAccount account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        for (TempDeck deck : account.getDecks()) {
            this.decks.add(new Deck(deck, collection));
        }
        this.mainDeck = getDeck(account.getMainDeckName());
        this.money = account.getMoney();
        this.wins = account.getWins();
    }

    public int getWins() {
        return wins;
    }

    public String getUsername() {
        return this.username;
    }

    public Collection getCollection() {
        return this.collection;
    }

    public ArrayList<Deck> getDecks() {
        return this.decks;
    }

    public Deck getMainDeck() {
        return this.mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<MatchHistory> getMatchHistories() {
        return this.matchHistories;
    }

    public int getMoney() {
        return this.money;
    }

    public void changeMoney(int change) {

    }

    public Deck getDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.areSame(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public String getPassword() {
        return password;
    }
}