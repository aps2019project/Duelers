package server.models.account;

import server.models.card.Card;

import java.util.ArrayList;

public class Collection {

    private ArrayList<Card> heroes = new ArrayList<>();
    private ArrayList<Card> minions = new ArrayList<>();
    private ArrayList<Card> spells = new ArrayList<>();
    private ArrayList<Card> items = new ArrayList<>();

    public ArrayList<Card> getHeroes() {
        return this.heroes;
    }

    public void addCard(Card card) {
        switch (card.getType()) {
            case HERO:
                addHero(card);
                break;
            case MINION:
                addMinion(card);
                break;
            case SPELL:
                addSpell(card);
                break;
            case USABLE_ITEM:
            case COLLECTIBLE_ITEM:
                addItem(card);
                break;
        }
    }

    public void addHero(Card hero) {
        heroes.add(hero);
    }

    public void removeHero(Card hero) {

    }

    public ArrayList<Card> getMinions() {
        return this.minions;
    }

    public void addMinion(Card minion) {
        minions.add(minion);
    }

    public void removeMinion(Card minion) {

    }

    public ArrayList<Card> getSpells() {
        return this.spells;
    }

    public void addSpell(Card spell) {
        spells.add(spell);
    }

    public void removeSpell(Card spell) {

    }

    public ArrayList<Card> getItems() {
        return this.items;
    }

    public void addItem(Card item) {
        items.add(item);
    }

    public void removeItem(Card item) {

    }

    public Card findHero(String heroId) {
        return findCardInList(heroId, heroes);
    }

    public Card findItem(String itemId) {
        return findCardInList(itemId, items);
    }

    public Card findOthers(String cardId) {
        Card card1 = findCardInList(cardId, minions);
        if (card1 != null) return card1;
        return findCardInList(cardId, spells);
    }

    private Card findCardInList(String cardId, ArrayList<Card> minions) {
        for (Card card : minions) {
            if (card.getCardId().equals(cardId)) return card;
        }
        return null;
    }
}