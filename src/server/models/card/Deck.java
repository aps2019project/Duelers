package server.models.card;

import server.Server;
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

    public Deck(TempDeck tempDeck, Collection originalCards) {
        this.deckName = tempDeck.getDeckName();
        this.hero = originalCards.getCard(tempDeck.getHeroId());
        this.item = originalCards.getCard(tempDeck.getItemId());
        for (String cardId : tempDeck.getOthersIds()) {
            others.add(originalCards.getCard(cardId));
        }
    }

    public Deck(String deckName) {
        this.deckName = deckName;
    }

    public boolean hasCard(String cardId){
        if(hero.getCardId().equals(cardId))
            return true;
        if(item.getCardId().equals(cardId))
            return true;
        for(Card card:others){
            if(card.getCardId().equals(cardId))
                return true;
        }
        return false;
    }

    public void addCard(String cardId, Collection collection) {
        if (collection.hasCard(cardId)) {
            addCard(collection.getCard(cardId));
        } else {
            Server.getInstance().serverPrint("Error!");
        }
    }

    private void addCard(Card card) {
        if (card == null || hasCard(card.getCardId())){
            Server.getInstance().serverPrint("Error!");
            return;
        }
        switch (card.getType()) {
            case HERO:
                hero = card;
                break;
            case USABLE_ITEM:
            case COLLECTIBLE_ITEM:
                item = card;
                break;
            case MINION:
            case SPELL:
                others.add(card);
                break;
            default:
                Server.getInstance().serverPrint("Error!");
                break;
        }
    }

    public void removeCard(Card card) {
        if (!hasCard(card.getCardId())){
            Server.getInstance().serverPrint("Error!");
            return;
        }
        if (hero == card)
            hero = null;
        if (item == card)
            item = null;
        others.remove(card);
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

    public String getDeckName() {
        return deckName;
    }

    public Card getHero() {
        return hero;
    }

    public ArrayList<Card> getOthers() {
        return others;
    }

    public Card getItem() {
        return item;
    }

}