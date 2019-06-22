package models.account;


import models.card.Deck;
import models.card.TempDeck;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Account {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String username;
    private String password;
    private Collection collection;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private ArrayList<MatchHistory> matchHistories;
    private int money;

    public Account(TempAccount account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        if (account.getDecks() != null) {
            for (TempDeck deck : account.getDecks()) {
                this.decks.add(new Deck(deck, collection));
            }
        }
        this.matchHistories = account.getMatchHistories();
        this.mainDeck = getDeck(account.getMainDeckName());
        this.money = account.getMoney();
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

    private Deck getDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.areSame(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public boolean isMainDeck(Deck deck) {
        return deck.equals(this.mainDeck);
    }

    public void update(TempAccount account) {
        if (!username.equals(account.getUsername())) {
            String old = username;
            username = account.getUsername();
            support.firePropertyChange("username", old, username);
        }
        if (!password.equals(account.getPassword())) {
            password = account.getPassword();
        }
        if (!collection.equals(account.getCollection())) {
            Collection old = collection;
            collection = account.getCollection();
            support.firePropertyChange("collection", old, collection);
        }
        if (money != account.getMoney()) {
            int old = money;
            money = account.getMoney();
            support.firePropertyChange("money", old, money);
        }
        if (!decksEqual(account.getDecks())) {
            ArrayList<Deck> newDecks = new ArrayList<>();

            for (TempDeck deck : account.getDecks()) {
                newDecks.add(new Deck(deck, collection));
            }
            ArrayList<Deck> old = decks;
            decks = newDecks;
            support.firePropertyChange("decks", old, decks);
            if (!mainDecksEqual(account)) {
                Deck oldMain = mainDeck;
                mainDeck = getDeck(account.getMainDeckName());
                support.firePropertyChange("main_deck", oldMain, mainDeck);
            }
        } else if (!mainDecksEqual(account)) {
            Deck old = mainDeck;
            mainDeck = getDeck(account.getMainDeckName());
            support.firePropertyChange("main_deck", old, mainDeck);
        }
        matchHistories = account.getMatchHistories();
    }

    private boolean mainDecksEqual(TempAccount account) {
        return (
                (mainDeck == null && account.getMainDeckName() == null) ||
                        (mainDeck != null && account.getMainDeckName() != null && mainDeck.getName().equals(account.getMainDeckName()))
        );
    }

    private boolean decksEqual(ArrayList<TempDeck> decks) {
        if (this.decks.size() != decks.size()) return false;

        for (TempDeck deck : decks) {
            if (!this.decks.contains(deck)) return false;
        }
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}