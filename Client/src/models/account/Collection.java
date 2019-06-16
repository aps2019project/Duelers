package models.account;

import models.card.Card;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> heroes;
    private ArrayList<Card> minions;
    private ArrayList<Card> spells;
    private ArrayList<Card> items;

    public ArrayList<Card> getHeroes() {
        return this.heroes;
    }

    public ArrayList<Card> getMinions() {
        return this.minions;
    }

    public ArrayList<Card> getSpells() {
        return this.spells;
    }

    public ArrayList<Card> getItems() {
        return this.items;
    }

    public ArrayList<Card> searchCollection(String cardName) {
        ArrayList<Card> results = new ArrayList<>();
        searchInList(heroes, results, cardName);
        searchInList(minions, results, cardName);
        searchInList(spells, results, cardName);
        searchInList(items, results, cardName);
        return results;
    }

    private void searchInList(ArrayList<Card> list, ArrayList<Card> results, String cardName) {
        for (Card card : list) {
            if (card.areSame(cardName)) {
                results.add(card);
            }
        }
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
            if (card.getCardId().equalsIgnoreCase(cardId)) return card;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection)) return false;
        Collection other = (Collection) o;

        if (heroes.size() != other.heroes.size() ||
                minions.size() != other.minions.size() ||
                spells.size() != other.spells.size() ||
                items.size() != other.items.size()
        ) return false;

        for (Card card : heroes) {
            if (!other.heroes.contains(card)) return false;
        }

        for (Card card : minions) {
            if (!other.minions.contains(card)) return false;
        }

        for (Card card : spells) {
            if (!other.spells.contains(card)) return false;
        }

        for (Card card : items) {
            if (!other.items.contains(card)) return false;
        }

        return true;
    }
}