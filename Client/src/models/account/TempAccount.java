package models.account;

import models.card.TempDeck;

import java.util.ArrayList;

public class TempAccount {
    private String username;
    private String password;
    private Collection collection;
    private ArrayList<TempDeck> decks = new ArrayList<>();
    private String mainDeckName;
    private ArrayList<MatchHistory> matchHistories = new ArrayList<>();
    private int money;
    private int wins;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection getCollection() {
        return collection;
    }

    public ArrayList<TempDeck> getDecks() {
        return decks;
    }

    public String getMainDeckName() {
        return mainDeckName;
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
}
