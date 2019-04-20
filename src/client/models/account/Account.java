package client.models.account;

import client.models.card.Deck;

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

    public ArrayList<MatchHistory> getMatchHistories() {
        return this.matchHistories;
    }

    public int getMoney() {
        return this.money;
    }
}