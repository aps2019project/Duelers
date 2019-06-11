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


    public void selectUser(String userName) {
        if (Client.getInstance().getAccount().getUsername().equalsIgnoreCase(userName)) {
            //TODO:Exception
        }
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeSelectUserMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, userName, 0
                )
        );
    }
}
