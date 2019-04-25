package client.models.account;

import client.models.card.Deck;
import client.view.request.InputException;

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

    public ArrayList<MatchHistory> getMatchHistories() {
        return this.matchHistories;
    }

    public int getMoney() {
        return this.money;
    }

    public Deck getDeck(String deckName) throws InputException {
        for (Deck deck : decks) {
            if (deck.areSame(deckName)) {
                return deck;
            }
        }
        throw new InputException("Such deck not found");
    }
}