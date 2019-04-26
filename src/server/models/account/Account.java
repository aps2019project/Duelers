package server.models.account;

import server.models.card.Deck;

import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private Collection collection;
    private ArrayList<Deck> decks;
    private Deck mainDeck;
    private ArrayList<MatchHistory> matchHistories;
    private int money;
    private int wins;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.money = 15000;
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