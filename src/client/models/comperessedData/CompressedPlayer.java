package client.models.comperessedData;

import client.models.card.CardType;

import java.util.ArrayList;
import java.util.Iterator;

public class CompressedPlayer {
    private String userName;
    private int currentMP;
    private ArrayList<CompressedCard> hand = new ArrayList<>();
    private ArrayList<CompressedCard> graveyard = new ArrayList<>();
    private CompressedCard nextCard;
    private ArrayList<CompressedCard> collectedItems = new ArrayList<>();
    private int playerNumber;
    private int numberOfCollectedFlags;
    private ArrayList<CompressedTroop> troops = new ArrayList<>();
    private CompressedTroop hero;


    public void addCardToHand(CompressedCard card) {
        hand.add(card);
        if (hand.size() > 5)
            System.out.println("Client Game Error!");
    }

    public void addCardToNext(CompressedCard card) {
        if (nextCard != null)
            System.out.println("Client Game Error!");
        else
            nextCard = card;
    }

    public void addCardToCollectedItems(CompressedCard card) {
        collectedItems.add(card);
    }

    public void addCardToGraveYard(CompressedCard card) {
        graveyard.add(card);
    }

    public void troopUpdate(CompressedTroop troop) {
        if(troops==null)
            troops=new ArrayList<>();
        removeTroop(troop.getCard().getCardId());
        troops.add(troop);
        if (troop.getCard().getType() == CardType.HERO)
            hero = troop;
    }

    public void removeCardFromHand(String cardId) {
        Iterator<CompressedCard> iterator = hand.iterator();
        while (iterator.hasNext()) {//TODO:Ahmad Check syntax
            if (iterator.next().getCardId().equals(cardId))
                iterator.remove();
        }
    }

    public void removeCardFromNext(String cardId) {
        if (nextCard.getCardId().equals(cardId)) {
            nextCard = null;
        } else {
            System.out.println("Client Game Error!");
        }
    }

    public void removeCardFromCollectedItems(String cardId) {
        Iterator<CompressedCard> iterator = collectedItems.iterator();
        while (iterator.hasNext()) {//TODO:Ahmad Check syntax
            if (iterator.next().getCardId().equals(cardId))
                iterator.remove();
        }
    }

    public void removeTroop(String cardId) {
        if(troops==null)
            troops=new ArrayList<>();
        Iterator<CompressedTroop> iterator = troops.iterator();
        while (iterator.hasNext()) {//TODO:Ahmad Check syntax
            if (iterator.next().getCard().getCardId().equals(cardId))
                iterator.remove();
        }
        if (hero.getCard().getCardId().equals(cardId))
            hero = null;
    }

    public CompressedCard searchHand(String cardId) {
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

    public void setCurrentMP(int currentMP) {
        this.currentMP = currentMP;
    }

    public void setNumberOfCollectedFlags(int numberOfCollectedFlags) {
        this.numberOfCollectedFlags = numberOfCollectedFlags;
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
}
