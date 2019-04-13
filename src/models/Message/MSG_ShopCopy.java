package models.Message;

import models.card.Card;
import models.menus.Shop;

import java.util.ArrayList;

public class MSG_ShopCopy extends Message {
    private ArrayList<Card> cards;

    public MSG_ShopCopy(String sender, String receiver) {
        super(sender, receiver);
        this.cards = Shop.getInstance().getCards();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
