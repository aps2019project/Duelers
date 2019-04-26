package server.models.account;

import server.Server;
import server.models.card.Card;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> heroes = new ArrayList<>();
    private ArrayList<Card> minions = new ArrayList<>();
    private ArrayList<Card> spells = new ArrayList<>();
    private ArrayList<Card> items = new ArrayList<>();

    public boolean hasCard(String cardId) {
        return hasCard(cardId, heroes) || hasCard(cardId, minions) || hasCard(cardId, spells) || hasCard(cardId, items);
    }

    private boolean hasCard(String cardId, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return true;
        }
        return false;
    }

    public Card getCard(String cardId) {
        if (hasCard(cardId, heroes))
            return getCard(cardId, heroes);
        if (hasCard(cardId, minions))
            return getCard(cardId, minions);
        if (hasCard(cardId, spells))
            return getCard(cardId, spells);
        if (hasCard(cardId, items))
            return getCard(cardId, items);
        return null;
    }

    private Card getCard(String cardId, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return card;
        }
        return null;
    }

    public void addCard(String cardName, Collection originalCards, String username) {//for account collections
        if (!originalCards.hasCard(cardName)) {
            Server.getInstance().serverPrint("Invalid CardName!");
            return;
        }
        int number = 1;
        String cardId = (username + "_" + cardName + "_").replaceAll("\\s+", "");
        while (hasCard(cardId + number))
            number++;
        Card newCard = new Card(originalCards.getCard(cardName), username, number);
        addCard(newCard);
    }

    public void addCard(Card card) {//for shop
        if (card == null) {
            Server.getInstance().serverPrint("Null Card.");
            return;
        }
        if (hasCard(card.getCardId())) {
            Server.getInstance().serverPrint("Add Card Error.");
            return;
        }
        switch (card.getType()) {
            case HERO:
                heroes.add(card);
                break;
            case MINION:
                minions.add(card);
                break;
            case SPELL:
                spells.add(card);
                break;
            case USABLE_ITEM:
            case COLLECTIBLE_ITEM:
                items.add(card);
                break;
            case FLAG:
                Server.getInstance().serverPrint("Error");
                break;
        }
    }

    public void removeCard(Card card) {
        heroes.remove(card);
        minions.remove(card);
        spells.remove(card);
        items.remove(card);
    }

    public ArrayList<Card> getHeroes() {
        return heroes;
    }

    public ArrayList<Card> getMinions() {
        return minions;
    }

    public ArrayList<Card> getSpells() {
        return spells;
    }

    public ArrayList<Card> getItems() {
        return items;
    }
}