package client.models.comperessedData;

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
        for (CompressedCard card :
                hand) {
            if (card.getCardId().equals(cardId)){
                return card;
            }
        }
        return null;
    }

    public CompressedCard searchCollectedItems(String cardId) {
        for (CompressedCard card :
                collectedItems) {
            if (card.getCardId().equals(cardId)){
                return card;
            }
        }
        return null;
    }
}
