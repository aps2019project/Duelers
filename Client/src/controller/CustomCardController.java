package controller;

import models.Constants;
import models.card.EditableCard;
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

    public void createCard(EditableCard card) {
        try {
            card.checkValidation();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeCustomCardMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, card.toImmutableCard(), 0)
            );
        } catch (InputException e) {
            Client.getInstance().getCurrentShow().showError(e.getMessage());
        }
    }
}
