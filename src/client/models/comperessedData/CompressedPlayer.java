package client.models.comperessedData;

import client.models.card.CardType;

import java.util.ArrayList;

public class CompressedPlayer {
    private String userName;
    private int currentMP;
    private ArrayList<CompressedCard> hand = new ArrayList<>();
    private ArrayList<CompressedCard> graveyard = new ArrayList<>();
    private CompressedCard nextCard;
    private ArrayList<CompressedCard> collectedItems = new ArrayList<>();
    private int playerNumber;
    private int numberOfCollectedFlags;
    private ArrayList<CompressedTroop> troops = new ArrayList<>(); // TODO: implementing needed;
    private CompressedTroop hero; // TODO: implementing needed;

    public String getUserName() {
        return userName;
    }

    public int getCurrentMP() {
        return currentMP;
    }

    public ArrayList<CompressedCard> getHand() {
        return hand;
    }

    public ArrayList<CompressedCard> getGraveyard() {
        return graveyard;
    }

    public CompressedCard getNextCard() {
        return nextCard;
    }

    public ArrayList<CompressedCard> getCollectedItems() {
        return collectedItems;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getNumberOfCollectedFlags() {
        return numberOfCollectedFlags;
    }

    public CompressedCard searchCard(String cardId) {
        for (CompressedCard card : hand) {
            if (card.getCardId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }

    public CompressedTroop searchTroop(String cardId) {
        for (CompressedTroop troop : troops) {
            if (troop.getCard().getCardId().equals(cardId)) {
                return troop;
            }
        }
        return null;
    }

    public CompressedCard searchCollectedItems(String cardId) {
        for (CompressedCard card : collectedItems) {
            if (card.getCardId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }

    public ArrayList<CompressedTroop> getTroops() {
        return troops;
    }

    public void setTroops(ArrayList<CompressedTroop> troops) {
        this.troops = troops;

        for (CompressedTroop troop : troops) {
            if (troop.getCard().getType() == CardType.HERO) {
                hero = troop;
            }
        }
    }

    public CompressedTroop getHero() {
        return hero;
    }

    public CompressedCard searchGraveyard(String cardId) {
        for (CompressedCard card : graveyard) {
            if (card.getCardId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }
}
