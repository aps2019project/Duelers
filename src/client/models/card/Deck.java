package client.models.card;

import client.models.account.Collection;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private Card hero;
    private Card item;
    private ArrayList<Card> others = new ArrayList<>();

    public Deck(TempDeck tempDeck, Collection collection) {
        this.deckName = tempDeck.getDeckName();
        this.hero = collection.findHero(tempDeck.getHeroId());
        this.item = collection.findItem(tempDeck.getItemId());
        for (String cardId : tempDeck.getOthersIds()) {
            others.add(collection.findOthers(cardId));
        }
    }

    public String getName() {
        return this.deckName;
    }

    public Card getHero() {
        return this.hero;
    }

    public ArrayList<Card> getOthers() {
        return this.others;
    }

    public int getPopulation() {
        return 0;
    }

    public Card getItem() {
        return this.item;
    }

    public boolean areSame(String deckName) {
        return this.deckName.equals(deckName);
    }

    public boolean isValid() {
        if (hero == null) return false;
        return others.size() == 20;
    }
}