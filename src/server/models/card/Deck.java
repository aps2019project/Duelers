package server.models.card;

import server.models.account.Collection;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private Card hero;
    private Card item;
    private ArrayList<Card> others = new ArrayList<>();

    public Deck(String deckName, Card hero, Card item, ArrayList<Card> others) {
        this.deckName = deckName;
        this.hero = hero;
        this.item = item;
        this.others = others;
    }

    public Deck(TempDeck tempDeck, Collection collection) {
        this.deckName = tempDeck.getDeckName();
        this.hero = collection.findHero(tempDeck.getHeroId());
        this.item = collection.findItem(tempDeck.getItemId());
        for (String cardId : tempDeck.getOthersIds()) {
            others.add(collection.findOthers(cardId));
        }
    }

    public Deck(String deckName) {
        this.deckName=deckName;
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

    public boolean isValid() {
        if (hero == null) return false;
        return others.size() == 20;
    }
}