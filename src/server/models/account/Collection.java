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
}