package client.models.menus;

import client.models.account.Account;
import client.models.card.Card;

import java.util.ArrayList;

public class Shop extends Menu {
    private static  Shop SHOP ;
    private ArrayList<Card> cards;

    private Shop() {
        //read from File
    }

    public static Shop getInstance() {
        if (SHOP == null) {
            SHOP = new Shop();
        }
        return SHOP;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {

    }

    public void removeCard(Card card) {

    }

    public void buy(Account account, Card card) {

    }

    public void sell(Account account, Card card) {

    }

    public Card searchCard(String cardName) {
        return null;
    }
}