package server.detaCenter.models.account;

import server.detaCenter.models.card.Deck;
import server.detaCenter.models.card.TempDeck;

import java.util.ArrayList;

public class TempAccount {
    private String username;
    private String password;
    private Collection collection;
    private ArrayList<TempDeck> decks = new ArrayList<>();
    private String mainDeckName;
    private ArrayList<MatchHistory> matchHistories;
    private int money;
    private int wins;

    public TempAccount(Account account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        for (Deck deck : account.getDecks()) {
            this.decks.add(new TempDeck(deck));
        }
        if (account.getMainDeck() != null) {
            this.mainDeckName = account.getMainDeck().getDeckName();
        }
        this.matchHistories = account.getMatchHistories();
        this.money = account.getMoney();
        this.wins = account.getWins();
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
