package server.dataCenter.models.account;

import server.dataCenter.models.card.Card;
import server.dataCenter.models.card.Deck;
import server.dataCenter.models.card.TempDeck;
import server.exceptions.ClientException;
import server.exceptions.LogicException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private List<MatchHistory> matchHistories = new ArrayList<>();
    private int money;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.money = 15000;
        this.collection = new Collection();
    }

    public Account(TempAccount account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = account.getCollection();
        if (account.getDecks() != null) {
            for (TempDeck deck : account.getDecks()) {
                this.decks.add(new Deck(deck, collection));
            }
        }
        if (account.getMainDeckName() != null)
            this.mainDeck = getDeck(account.getMainDeckName());
        this.money = account.getMoney();
        this.matchHistories = account.getMatchHistories();
    }

    private boolean hasDeck(String deckName) {
        if (deckName == null)
            return false;
        for (Deck deck : decks) {
            if (deck.getDeckName().equalsIgnoreCase(deckName))
                return true;
        }
        return false;
    }

    public Deck getDeck(String deckName) {
        if (deckName == null)
            return null;
        for (Deck deck : decks) {
            if (deck.getDeckName().equalsIgnoreCase(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public void addDeck(String deckName) throws LogicException {
        if (hasDeck(deckName)) {
            throw new ClientException("new deck's name was duplicate.");
        }
        decks.add(new Deck(deckName));
    }

    public void addDeck(Deck deck) throws ClientException {
        if (hasDeck(deck.getDeckName())) {
            throw new ClientException("new deck's name was duplicate.");
        }
        decks.add(deck);
    }

    public void deleteDeck(String deckName) throws LogicException {
        if (!hasDeck(deckName)) {
            throw new ClientException("deck was not found.");
        }
        decks.remove(getDeck(deckName));
    }

    public void buyCard(String cardName, int price, Collection originalCards) throws LogicException {
        if (!originalCards.hasCard(cardName)) {
            throw new ClientException("invalid card name");
        }
        if (price > money) {
            throw new ClientException("account's money isn't enough.");
        }
        collection.addCard(cardName, originalCards, username);
        money -= price;
    }

    public void sellCard(String cardId) throws LogicException {
        if (!collection.hasCard(cardId)) {
            throw new ClientException("invalid card id");
        }
        money += collection.getCard(cardId).getPrice();
        Card card = collection.getCard(cardId);
        collection.removeCard(card);
        for (Deck deck : decks) {
            if (deck.hasCard(cardId)) {
                deck.removeCard(card);
            }
        }
    }

    public void addCardToDeck(String cardId, String deckName) throws LogicException {
        if (!hasDeck(deckName)) {
            throw new ClientException("deck was not found.");
        } else if (!collection.hasCard(cardId)) {
            throw new ClientException("invalid card id.");
        } else {
            getDeck(deckName).addCard(cardId, collection);
        }
    }

    public void removeCardFromDeck(String cardId, String deckName) throws LogicException {
        if (!hasDeck(deckName)) {
            throw new ClientException("deck was not found.");
        } else {
            getDeck(deckName).removeCard(collection.getCard(cardId));
        }
    }

    public void selectDeck(String deckName) throws LogicException {
        if (!hasDeck(deckName)) {
            throw new ClientException("deck was not found.");
        } else {
            mainDeck = getDeck(deckName);
        }
    }

    public void addMatchHistory(MatchHistory matchHistory, int reward) {
        matchHistories.add(matchHistory);
        if (matchHistory.isAmIWinner()) {
            money += reward;
        }
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

    public Deck getMainDeck() {
        return mainDeck;
    }

    public boolean hasValidMainDeck() {
        return mainDeck != null && mainDeck.isValid();
    }

    List<MatchHistory> getMatchHistories() {
        return Collections.unmodifiableList(matchHistories);
    }

    int getMoney() {
        return money;
    }

    public int getWins() {
        return matchHistories.stream().filter(MatchHistory::isAmIWinner).collect(Collectors.toList()).size();
    }

    List<Deck> getDecks() {
        return Collections.unmodifiableList(decks);
    }
}