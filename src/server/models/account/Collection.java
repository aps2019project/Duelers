package server.models.account;

import server.Server;
import server.models.card.Card;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> heroes = new ArrayList<>();
    private ArrayList<Card> minions = new ArrayList<>();
    private ArrayList<Card> spells = new ArrayList<>();
    private ArrayList<Card> items = new ArrayList<>();


    public void addCard(Card card) {
        if (card == null) {
            Server.getInstance().serverPrint("Null Card.");
            return;
        }
        if (hasCard(card)) {
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
        }
    }

    public boolean hasCard(Card card) {
        return heroes.contains(card) || minions.contains(card) || spells.contains(card) || items.contains(card);
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

    public Card findHero(String heroId) {
        return findCardInList(heroId, heroes);
    }

    public Card findItem(String itemId) {
        return findCardInList(itemId, items);
    }

    public Card findOthers(String cardId) {
        Card card = findCardInList(cardId, minions);
        if (card != null) return card;
        return findCardInList(cardId, spells);
    }

    private Card findCardInList(String cardId, ArrayList<Card> minions) {
        for (Card card : minions) {
            if (card.getCardId().equals(cardId)) return card;
        }
        return null;
    }
}