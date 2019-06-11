package controller;

import javafx.application.Platform;
import models.Constants;
import models.exceptions.InputException;
import models.game.GameType;
import models.message.Message;
import view.MultiPlayerMenu;

public class MultiPlayerMenuController {
    private static MultiPlayerMenuController ourInstance = new MultiPlayerMenuController();

    private MultiPlayerMenuController() {
    }

    public static MultiPlayerMenuController getInstance() {
        return ourInstance;
    }

    public void startGame(GameType gameType, int numberOfFlags, String opponent) {
        try {
            if (opponent == null || opponent.length() < 2)
                throw new InputException("invalid opponent");
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeNewMultiPlayerGameMessage(
                            Client.getInstance().getClientName(), Constants.SERVER_NAME, gameType,
                            numberOfFlags, opponent, 0
                    )
            );
        } catch (InputException e) {
            Platform.runLater(() -> MultiPlayerMenu.getInstance().showError(e.getMessage()));
        }
    }
}
