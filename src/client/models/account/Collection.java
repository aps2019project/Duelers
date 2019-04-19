package client.models.account;

import client.models.card.Card;

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
}