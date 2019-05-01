package server.models.message;

import server.models.card.Card;

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

    public CompressedPlayer(String userName, int currentMP, ArrayList<Card> hand, ArrayList<Card> graveyard,
                            Card nextCard, ArrayList<Card> collectedItems, int playerNumber, int numberOfCollectedFlags) {
        this.userName = userName;
        this.currentMP = currentMP;
        for (Card card : hand)
            this.hand.add(card.toCompressedCard());
        for (Card card : graveyard)
            this.graveyard.add(card.toCompressedCard());
        this.nextCard = nextCard.toCompressedCard();
        for (Card card : collectedItems)
            this.collectedItems.add(card.toCompressedCard());
        this.playerNumber = playerNumber;
        this.numberOfCollectedFlags = numberOfCollectedFlags;
    }
}
