package client.models.game;

import client.models.account.Account;
import client.models.card.Card;
import client.models.card.CardType;
import client.models.card.Deck;

import java.util.ArrayList;

public class Player {
    private String userName;
    private int currentMP;
    private Deck deck;
    private Card[] hand;
    private ArrayList<Troop> troops;
    private ArrayList<Card> graveyard;
    private Card nextCard;
    private ArrayList<Card> collectedItems;
    private ArrayList<Troop> flagCarriers = new ArrayList<>();

    public Player(Account account) {

    }

    public String getUserName() {
        return this.userName;
    }

    public int getCurrentMP() {
        return this.currentMP;
    }

    public ArrayList<Troop> getFlagCarriers() {
        return flagCarriers;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Card[] getHand() {
        return this.hand;
    }

    public ArrayList<Troop> getTroops() {
        return this.troops;
    }

    public ArrayList<Card> getGraveyard() {
        return this.graveyard;
    }

    public Card getNextCard() {
        return this.nextCard;
    }

    public ArrayList<Card> getCollectedItems() {
        return this.collectedItems;
    }

    public Troop getHero() {
        for (Troop troop : troops) {
            if (troop.getCard().getType() == CardType.HERO) {
                return troop;
            }
        }
        return null;
    }

    public Troop searchTroop(String cardId) {
        for (Troop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardId)) return troop;
        }
        return null;
    }

    public Card searchCard(String cardId) {
        for (Card card : hand) {
            if (card.getCardId().equalsIgnoreCase(cardId)) return card;
        }
        return null;
    }

    public Card searchCollectedItems(String cardId) {
        for (Card item : collectedItems) {
            if (item.getCardId().equalsIgnoreCase(cardId)) return item;
        }
        return null;
    }

    public Card searchGraveyard(String cardId) {
        for (Card card : graveyard) {
            if (card.getCardId().equalsIgnoreCase(cardId)) return card;
        }
        return null;
    }

    public Troop getTroop(String cardId) {
        for (Troop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardId)) {
                return troop;
            }
        }
        return null;
    }
}