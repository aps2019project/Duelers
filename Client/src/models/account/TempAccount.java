package models.account;

import models.card.TempDeck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TempAccount {
    private String username;
    private String password;
    private Collection collection;
    private List<TempDeck> decks = new ArrayList<>();
    private String mainDeckName;
    private List<MatchHistory> matchHistories = new ArrayList<>();
    private int money;
    private int wins;

    public String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public Collection getCollection() {
        return collection;
    }

    List<TempDeck> getDecks() {
        return Collections.unmodifiableList(decks);
    }

    String getMainDeckName() {
        return mainDeckName;
    }

    List<MatchHistory> getMatchHistories() {
        return Collections.unmodifiableList(matchHistories);
    }

    int getMoney() {
        return money;
    }

    public int getWins() {
        return wins;
    }
}
