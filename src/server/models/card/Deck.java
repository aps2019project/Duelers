package server.models.card;

import server.Server;
import server.models.account.Collection;
import server.models.exceptions.ClientException;
import server.models.exceptions.LogicException;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private Card hero;
    private Card item;
    private ArrayList<Card> others = new ArrayList<>();

    //ERROR:This uses same reference!
    public Deck(String deckName, Card hero, Card item, ArrayList<Card> others) {
        this.deckName = deckName;
        this.hero = hero;
        this.item = item;
        this.others = others;
    }

    public Deck(Deck deck) {
        this.deckName = deck.deckName;
        if (deck.hero != null) {
            this.hero = new Card(deck.hero);
        }
        if (deck.item != null) {
            this.item = new Card(deck.item);
        }
        for (Card card :
                deck.others) {
            others.add(new Card(card));
        }
    }

    public Deck(TempDeck tempDeck, Collection collection) {
        this.deckName = tempDeck.getDeckName();
        this.hero = collection.getCard(tempDeck.getHeroId());
        this.item = collection.getCard(tempDeck.getItemId());
        for (String cardId : tempDeck.getOthersIds()) {
            others.add(collection.getCard(cardId));
        }
    }

    public Deck(String deckName) {
        this.deckName = deckName;
    }

    public boolean hasCard(String cardId) {
        if (hero != null && hero.getCardId().equalsIgnoreCase(cardId))
            return true;
        if (item != null && item.getCardId().equalsIgnoreCase(cardId))
            return true;
        for (Card card : others) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return true;
        }
        return false;
    }

    public void addCard(String cardId, Collection collection) throws LogicException {
        if (hasCard(cardId)) {
            throw new ClientException("deck had this card.");
        }
        addCard(collection.getCard(cardId));
    }

    private void addCard(Card card) {
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

    public void removeCard(Card card) throws LogicException {
        if (!hasCard(card.getCardId())) {
            throw new ClientException("deck doesn't have this card.");
        }
        if (hero == card)
            hero = null;
        if (item == card)
            item = null;
        others.remove(card);
    }

    public boolean isValid() {//TODO:reCode
        if (hero == null) return false;
        return others.size() == 20;
    }

    public void copyCards() {//TODO:reCode
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

    private String makeId(Card card, int number) {//TODO:reCode
        return deckName.replaceAll(" ", "") + "_" +
                card.getName().replaceAll(" ", "") + "_" +
                number;
    }

    private int numberOf(String name) {//Todo:reCode
        if (hero.getName().equalsIgnoreCase(name) || item.getName().equalsIgnoreCase(name)) return 0;
        int number = 0;
        for (Card card : others) {
            if (card.getName().equalsIgnoreCase(name)) number++;
        }
        return number;
    }

    public String getDeckName() {
        return deckName;
    }

    public Card getHero() {
        return hero;
    }

    public Card getCardFromOthers(String cardId) {
        for (Card card :
                others) {
            if (cardId.equalsIgnoreCase(card.getCardId())) {
                return card;
            }
        }
        return null;
    }

    public ArrayList<Card> getOthers() {
        return others;
    }

    public Card getItem() {
        return item;
    }
}