package client.models.card;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private Card hero;
    private Card item;
    private ArrayList<Card> others;

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