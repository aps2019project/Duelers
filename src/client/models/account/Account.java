package client.models.account;

import client.models.card.Deck;
import client.models.card.TempDeck;
import client.view.request.InputException;

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

    public Account(TempAccount account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        if (account.getDecks() != null) {
            for (TempDeck deck : account.getDecks()) {
                this.decks.add(new Deck(deck, collection));
            }
        }
        try {
            this.mainDeck = getDeck(account.getMainDeckName());
        } catch (InputException e) {
            this.mainDeck = null;
        }
        this.money = account.getMoney();
        this.wins = account.getWins();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().getName().equals(AccountInfo.class.getName())) {
            AccountInfo accountInfo = (AccountInfo) obj;
            if (this.username.equals(accountInfo.getUsername())) return true;
        }
        if (!obj.getClass().getName().equals(Account.class.getName())) return false;
        Account account = (Account) obj;
        return this.username.equals(account.username);
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