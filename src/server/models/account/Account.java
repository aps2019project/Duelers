package server.models.account;

import server.models.card.Deck;

import java.util.ArrayList;

public class Account {

    private String userName;
    private String passWord;
    private Collection collection;
    private ArrayList<Deck> decks;
    private Deck mainDeck;
    private ArrayList<MatchHistory> matchHistories;
    private int money;
    private int wins;
    
    public int getWins() {
        return wins;
    }
    
    public String getUserName() {
        return this.userName;
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

    public Account(String userName) {

    }
}