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

    public void startGame(int mode, int numberOfFlags , String opponent) {

        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeNewMultiPlayerGameMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, GameType.values()[mode - 1],
                        numberOfFlags, opponent, 0
                )
        );
    }
}
