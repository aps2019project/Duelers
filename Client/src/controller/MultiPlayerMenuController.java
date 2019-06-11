package controller;

import models.Constants;
import models.game.GameType;
import models.message.Message;

public class MultiPlayerMenuController {
    private static MultiPlayerMenuController ourInstance = new MultiPlayerMenuController();

    private MultiPlayerMenuController() {
    }

    public static MultiPlayerMenuController getInstance() {
        return ourInstance;
    }

    public void startGame(GameType gameType, int numberOfFlags , String opponent) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeNewMultiPlayerGameMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, gameType,
                        numberOfFlags, opponent, 0
                )
        );
    }
}
