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

    public void copyCards() {
        this.hero = new Card(hero);
        this.hero.setCardId(makeId(hero, 1));
        this.item = new Card(item);
        this.item.setCardId(makeId(item, 1));
        ArrayList<Card> oldOthers = this.others;
        this.others = new ArrayList<>();
        for (Card other : oldOthers) {
            Card card = new Card(other);
            card.setCardId(makeId(card, numberOf(card.getName()) + 1));
            others.add(card);
        }
    }

    private String makeId(Card card, int number) {
        return deckName.replaceAll(" ", "") + "_" +
                card.getName().replaceAll(" ", "") + "_" +
                number;
    }

    private int numberOf(String name) {
        if (hero.areSame(name) || item.areSame(name)) return 0;
        int number = 0;
        for (Card card : others) {
            if (card.areSame(name)) number++;
        }
        return number;
    }
}