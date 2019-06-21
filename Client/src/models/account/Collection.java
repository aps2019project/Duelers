package models.account;

import models.card.Card;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> heroes = new ArrayList<>();
    private ArrayList<Card> minions = new ArrayList<>();
    private ArrayList<Card> spells = new ArrayList<>();
    private ArrayList<Card> items = new ArrayList<>();

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

    public Collection searchCollection(String cardName) {
        Collection result = new Collection();
        searchInList(heroes, result.heroes, cardName);
        searchInList(minions, result.minions, cardName);
        searchInList(spells, result.spells, cardName);
        searchInList(items, result.items, cardName);
        return result;
    }

    private void searchInList(ArrayList<Card> list, ArrayList<Card> results, String cardName) {
        for (Card card : list) {
            if (card.nameContains(cardName)) {
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

    public Card findOne(String cardName) {
        ArrayList<Card> result = new ArrayList<>();
        searchInList(heroes, result, cardName);
        searchInList(minions, result, cardName);
        searchInList(spells, result, cardName);
        searchInList(items, result, cardName);

        if (result.size() == 0) return null;
        return result.get(0);
    }
}