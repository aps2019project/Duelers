package server.models.card;

import java.util.ArrayList;

public class Deck {

    private String deckName;
    private Card hero;
    private Card item;
    private ArrayList<Card> others;


    public Deck(String name) {

    }

    public String getName() {
        return this.deckName;
    }

    public Card getHero() {
        return this.hero;
    }


    public void setHero(Card hero) {
        this.hero = hero;
    }


    public void removeHero(Card hero) {

    }

    public ArrayList<Card> getOthers() {
        return this.others;
    }


    public void addCard(Card item) {

    }


    public void removeCard(Card item) {

    }

    public int getPopulation() {
        return 0;
    }

    public Card getItem() {
        return this.item;
    }


    public void setItem(Card item) {
        this.item = item;
    }

    public boolean areSame(String deckName) {
        return this.deckName.equals(deckName);
    }
}