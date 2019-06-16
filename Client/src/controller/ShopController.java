package controller;

import models.Constants;
import models.account.Collection;
import models.card.Card;
import models.message.DataName;
import models.message.Message;

import java.util.ArrayList;

public class ShopController {
    private static ShopController ourInstance;
    private Collection originalCards = new Collection();

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (ourInstance == null) {
            ourInstance = new ShopController();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeGetDataMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, DataName.ORIGINAL_CARDS, 0)
            );
        }
        return ourInstance;
    }

    public void buy(String cardName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeBuyCardMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, cardName.replaceAll(" ", ""), 0
                )
        );
    }

    public void sell(String cardId) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeSellCardMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, cardId, 0
                )
        );
    }

    public Card searchInShop(String cardName) {
        return originalCards.searchCollection(cardName).get(0);
    }

    public ArrayList<Card> searchInCollection(String cardName) {
        return Client.getInstance().getAccount().getCollection().searchCollection(cardName);
    }

    void setOriginalCards(Collection originalCards) {
        this.originalCards = originalCards;
    }
}
