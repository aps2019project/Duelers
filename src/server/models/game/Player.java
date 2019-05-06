package server.models.game;

import server.Server;
import server.models.account.MatchHistory;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.comperessedData.CompressedPlayer;
import server.models.exceptions.ClientException;
import server.models.exceptions.ServerException;
import server.models.map.Cell;
import server.models.message.CardPosition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Player {
    private String userName;
    private int currentMP;
    private Deck deck;
    private Troop hero;
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Troop> troops = new ArrayList<>();
    private ArrayList<Card> graveyard = new ArrayList<>();
    private Card nextCard;
    private ArrayList<Card> collectedItems = new ArrayList<>();
    private ArrayList<Troop> flagCarriers = new ArrayList<>();
    private int playerNumber;
    private int numberOfCollectedFlags;
    private MatchHistory matchHistory;

    public Player(Deck mainDeck, String userName, int playerNumber) {
        this.playerNumber = playerNumber;
        this.userName = userName;
        deck = new Deck(mainDeck);
        setNextCard();
        for (int i = 0; i < 5; i++) {
            addNextCardToHand();
        }
    }

    public CompressedPlayer toCompressedPlayer() {
        return new CompressedPlayer(
                userName, currentMP, hand, graveyard, nextCard, collectedItems, playerNumber, numberOfCollectedFlags);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card insert(String cardId) throws ClientException {
        Card card = null;
        Iterator iterator = hand.iterator();
        while (iterator.hasNext()) {
            Card card1 = (Card) iterator.next();
            if (card1.getCardId().equalsIgnoreCase(cardId)) {
                card = card1;
                break;
            }
        }

        if (card == null) {
            iterator = collectedItems.iterator();
            while (iterator.hasNext()) {
                Card card1 = (Card) iterator.next();
                if (card1.getCardId().equalsIgnoreCase(cardId)) {
                    card = card1;
                    break;
                }
            }
        }

        if (card == null)
            throw new ClientException("card id is not valid");

        if (card.getMannaPoint() > currentMP)
            throw new ClientException("not enough manna point");

        iterator.remove();
        currentMP -= card.getMannaPoint();

        return card;
    }

    public void setNextCard() {
        int index = new Random().nextInt(deck.getOthers().size());
        nextCard = deck.getOthers().get(index);
        deck.getOthers().remove(nextCard);
    }

    public boolean addNextCardToHand() {
        if (hand.size() < 5) {
            hand.add(nextCard);
            setNextCard();
            return true;
        }
        return false;
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

    public void addFlagCarrier(Troop troop) {
        if (!this.flagCarriers.contains(troop))
            this.flagCarriers.add(troop);
    }

    public void removeFlagCarrier(Troop troop) {
        flagCarriers.remove(troop);
    }

    public void changeCurrentMP(int change) {
        currentMP += change;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public ArrayList<Troop> getTroops() {
        return this.troops;
    }

    public void addToGraveYard(Card card) {
        graveyard.add(card);
    }

    public Card getNextCard() {
        return this.nextCard;
    }

    public ArrayList<Card> getCollectedItems() {
        return this.collectedItems;
    }

    public void collectItem(Card card) {
        collectedItems.add(card);
    }

    public Troop getTroop(Cell cell) {
        for (Troop troop : troops) {
            if (troop.getCell().equals(cell)) {
                return troop;
            }
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

    public Troop getHero() {
        if (hero == null) {
            hero = new Troop(deck.getHero(), playerNumber);
            hero.setCanMove(true);
            hero.setCanAttack(true);
            troops.add(hero);
        }
        return hero;
    }

    public void setHero(Troop hero) {
        this.hero = hero;
    }

    public void killTroop(Game game, Troop troop) throws ServerException {
        addToGraveYard(troop.getCard());
        Server.getInstance().sendChangeCardPositionMessage(game, troop.getCard(), CardPosition.GRAVE_YARD);
        troops.remove(troop);
    }

    public int getNumberOfCollectedFlags() {
        return numberOfCollectedFlags;
    }

    public void increaseNumberOfCollectedFlags() {
        this.numberOfCollectedFlags++;
    }

    public void decreaseNumberOfCollectedFlags() {
        this.numberOfCollectedFlags--;
    }

    public MatchHistory getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(MatchHistory matchHistory) {
        this.matchHistory = matchHistory;
    }

    public void addTroop(Troop troop) {
        troops.add(troop);
    }
}