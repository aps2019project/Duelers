package controller;

import models.Constants;
import models.account.Collection;
import models.card.Card;
import models.exceptions.InputException;
import models.message.Message;

public class CustomCardController {
    private static CustomCardController customCardController;

    public static CustomCardController getInstance() {
        if (customCardController == null) {
            customCardController = new CustomCardController();
        }
        return customCardController;
    }

    public void createMinion(Card card) {
        try {
            card.checkValidation();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeCustomCardMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, card, 0)
            );
        } catch (InputException e) {
            Client.getInstance().getCurrentShow().showError(e.getMessage());
        }
    }
}
