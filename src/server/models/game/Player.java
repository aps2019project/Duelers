package server.models.game;

import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.map.Cell;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String userName;
    private int currentMP;
    private Deck deck;
    private Troop hero;
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Troop> troops;
    private ArrayList<Card> graveyard;
    private Card nextCard;
    private ArrayList<Card> collectedItems;
    private ArrayList<Troop> flagCarriers = new ArrayList<>();

    public Player(Account account) {
        deck = new Deck(account.getMainDeck());
        for (int i = 0; i < 5; i++) {
            setNextCard();
            addNextCardToHand();
        }
        setNextCard();
    }

    public void setNextCard() {
        int index = new Random().nextInt(deck.getOthers().size());
        nextCard = deck.getOthers().get(index);
    }

    public void addNextCardToHand() {
        if (hand.size() <= 5)
            hand.add(nextCard);
    }

    public String getUserName() {
        return this.userName;
    }

    public int getCurrentMP() {
        return this.currentMP;
    }

    public void setCurrentMP(int currentMP) {
        this.currentMP = currentMP;
    }

    public ArrayList<Troop> getFlagCarriers() {
        return flagCarriers;
    }

    public void changeCurrentMP(int change) {

    }

    public Deck getDeck() {
        return this.deck;
    }

    public Card collectFromDeck() {
        return null;
    }


    public void addToHand(Card card) {

    }


    public Card collectFromHand(String cardId) {
        return null;
    }

    public ArrayList<Troop> getTroops() {
        return this.troops;
    }

    public ArrayList<Card> getGraveyard() {
        return this.graveyard;
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

    public Troop getTroop(Cell cell) {
        for (Troop troop : troops) {
            if (troop.getCell().equals(cell)) {
                return troop;
            }
        }
        return null;
    }

    public Troop getHero() {
        return hero;
    }

    public void setHero(Troop hero) {
        hero = hero;
    }
}