package controller;

import models.Constants;
import models.account.Collection;
import models.message.DataName;
import models.message.Message;

public class ShopController {
    private static ShopController ourInstance;
    private Collection originalCards = new Collection();



    public static ShopController getInstance() {
        if (ourInstance == null) {
            ourInstance = new ShopController();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeGetDataMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, DataName.ORIGINAL_CARDS, 0)
            );
        }
        return ourInstance;
    }
    private ShopController() {
    }

    public void exit() {
        //TODO:change scene
    }

    public void buy(String cardName)  {
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

    public void setOriginalCards(Collection originalCards) {
        this.originalCards = originalCards;
    }
}
