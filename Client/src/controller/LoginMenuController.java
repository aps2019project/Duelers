package controller;

import javafx.application.Platform;
import models.Constants;
import models.exceptions.InputException;
import models.message.Message;
import view.LoginMenu;

public class LoginMenuController {
    private static LoginMenuController ourInstanse;

    public static LoginMenuController getInstance() {
        if (ourInstanse == null) {
            ourInstanse = new LoginMenuController();
        }
        return ourInstanse;
    }

    public void register(String userName, String password) {
        try {
            validateUsernameAndPassword(userName, password);
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeRegisterMessage(
                            Client.getInstance().getClientName(), Constants.SERVER_NAME, userName, password, 0)
            );
        } catch (InputException e) {
            Platform.runLater(() -> LoginMenu.getInstance().showError(e.getMessage()));
        }
    }

    private void validateUsernameAndPassword(String userName, String password) throws InputException {
        if (userName == null || userName.length() < 2) {
            throw new InputException("Invalid Username!");
        } else if (password == null || password.length() < 4) {
            throw new InputException("Invalid Password!");
        }
    }

    public void login(String userName, String password) {
        try {
            validateUsernameAndPassword(userName, password);
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeLogInMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, userName, password, 0)
            );
        } catch (InputException e) {
            Platform.runLater(() -> LoginMenu.getInstance().showError(e.getMessage()));
        }
    }
}
