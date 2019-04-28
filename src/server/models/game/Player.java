package server.models.game;

import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;

import java.util.ArrayList;

public class Player {
    private String userName;
    private int currentMP;
    private Deck deck;
    private Card[] hand;
    private ArrayList<Troop> troops;
    private ArrayList<Card> graveYard;
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

    public void setCurrentMP(int currentMP) {
        this.currentMP = currentMP;
    }


    public void changeCurrentMP(int change) {

    }

    public Deck getDeck() {
        return this.deck;
    }

    public Card collectFromDeck() {
        return null;
    }

    public Card[] getHand() {
        return this.hand;
    }


    public void setHand(Card[] hand) {
        this.hand = hand;
    }


    public void addToHand(Card card) {

    }


    public Card collectFromHand(String cardId) {
        return null;
    }

    public ArrayList<Troop> getTroops() {
        return this.troops;
    }

    public ArrayList<Card> getGraveYard() {
        return this.graveYard;
    }


    public void addToGraveYard(Card card) {

    }

    public Card getNextCard() {
        return this.nextCard;
    }


    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

    public ArrayList<Card> getCollectedItems() {
        return this.collectedItems;
    }

}