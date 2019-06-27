package models.account;

import models.card.Card;
import models.card.Deck;

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

    public void addCard(Card card) {//for shop
        if (card == null) {
            return;
        }
        if (hasCard(card.getCardId())) {
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
                break;
        }
    }

    private boolean hasCard(String cardId) {
        return hasCard(cardId, heroes) || hasCard(cardId, minions) || hasCard(cardId, spells) || hasCard(cardId, items);
    }

    private boolean hasCard(String cardId, ArrayList<Card> cards) {
        if (cardId == null || cards==null)
            return false;
        for (Card card : cards) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return true;
        }
        return false;
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
        findInList(heroes, result, cardName);
        findInList(minions, result, cardName);
        findInList(spells, result, cardName);
        findInList(items, result, cardName);

        if (result.size() == 0) return null;
        return result.get(0);
    }

    private void findInList(ArrayList<Card> list, ArrayList<Card> result, String cardName) {
        for (Card card : list) {
            if (card.isSameAs(cardName)) {
                result.add(card);
            }
        }
    }

    public int count(String cardName) {
        ArrayList<Card> result = new ArrayList<>();
        findInList(heroes, result, cardName);
        findInList(minions, result, cardName);
        findInList(spells, result, cardName);
        findInList(items, result, cardName);
        return result.size();
    }

    public Collection toShowing() {
        Collection collection = new Collection();
        convertListToShowing(collection.heroes, heroes);
        convertListToShowing(collection.spells, spells);
        convertListToShowing(collection.minions, minions);
        convertListToShowing(collection.items, items);
        return collection;
    }

    private void convertListToShowing(ArrayList<Card> newList, ArrayList<Card> mainList) {
        Outer:
        for (Card hero : mainList) {
            for (Card other : newList) {
                if (hero.isSameAs(other.getName())) continue Outer;
            }
            newList.add(hero);
        }
    }

    public String canAddCardTo(String cardName, Deck deck) {
        for (Card hero : heroes) {
            if (hero.isSameAs(cardName) && !deck.hasHero(hero)) {
                return hero.getCardId();
            }
        }
        for (Card item : items) {
            if (item.isSameAs(cardName) && !deck.hasItem(item)) {
                return item.getCardId();
            }
        }
        for (Card minion : minions) {
            if (minion.isSameAs(cardName) && !deck.hasCard(minion)) {
                return minion.getCardId();
            }
        }
        for (Card spell : spells) {
            if (spell.isSameAs(cardName) && !deck.hasCard(spell)) {
                return spell.getCardId();
            }
        }
        return null;
    }
}