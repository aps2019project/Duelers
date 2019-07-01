package server.detaCenter.models.account;

import server.Server;
import server.detaCenter.models.card.Card;
import server.detaCenter.models.card.CardType;
import server.exceptions.ClientException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Collection {
    private List<Card> heroes = new ArrayList<>();
    private List<Card> minions = new ArrayList<>();
    private List<Card> spells = new ArrayList<>();
    private List<Card> items = new ArrayList<>();

    boolean hasCard(String cardId) {
        return hasCard(cardId, heroes) || hasCard(cardId, minions) || hasCard(cardId, spells) || hasCard(cardId, items);
    }

    private boolean hasCard(String cardId, List<Card> cards) {
        if (cardId == null || cards==null)
            return false;
        for (Card card : cards) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return true;
        }
        return false;
    }

    public Card getCard(String cardId) {
        if (hasCard(cardId, heroes))
            return getCard(cardId, heroes);
        if (hasCard(cardId, minions))
            return getCard(cardId, minions);
        if (hasCard(cardId, spells))
            return getCard(cardId, spells);
        if (hasCard(cardId, items))
            return getCard(cardId, items);
        return null;
    }

    private Card getCard(String cardId, List<Card> cards) {
        for (Card card : cards) {
            if (card.getCardId().equalsIgnoreCase(cardId))
                return card;
        }
        return null;
    }

    void addCard(String cardName, Collection originalCards, String username) throws ClientException {//for account collections
        if (!originalCards.hasCard(cardName) || originalCards.getCard(cardName).getType() == CardType.COLLECTIBLE_ITEM) {
            Server.getInstance().serverPrint("Invalid CardName!");
            return;
        }
        int number = 1;
        String cardId = (username + "_" + cardName + "_").replaceAll(" ", "");
        while (hasCard(cardId + number))
            number++;
        Card newCard = new Card(originalCards.getCard(cardName), username, number);
        if (newCard.getType() == CardType.USABLE_ITEM && items.size() >= 3) {
            throw new ClientException("you can't have more than 3 items");
        }
        addCard(newCard);
    }

    public void addCard(Card card) {//for shop
        if (card == null) {
            Server.getInstance().serverPrint("Error!");
            return;
        }
        if (hasCard(card.getCardId())) {
            Server.getInstance().serverPrint("Error!");
            return;
        }
        switch (card.getType()) {
            case HERO:
                heroes.add(card);
                break;
            case MINION:
                minions.add(card);
                break;
            case SPELL:
                spells.add(card);
                break;
            case USABLE_ITEM:
            case COLLECTIBLE_ITEM:
                items.add(card);
                break;
            case FLAG:
                Server.getInstance().serverPrint("Error");
                break;
        }
    }

    void removeCard(Card card) {
        heroes.remove(card);
        minions.remove(card);
        spells.remove(card);
        items.remove(card);
    }

    public List<Card> getHeroes() {
        return Collections.unmodifiableList(heroes);
    }

    public List<Card> getMinions() {
        return Collections.unmodifiableList(minions);
    }

    public List<Card> getSpells() {
        return Collections.unmodifiableList(spells);
    }

    public List<Card> getItems() {
        return Collections.unmodifiableList(items);
    }
}