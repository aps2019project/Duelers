package controller;

import models.Constants;
import models.message.Message;

public class LoginMenuController {
    private static LoginMenuController ourInstanse;

    public static LoginMenuController getInstance() {
        if (ourInstanse == null) {
            ourInstanse = new LoginMenuController();
        }
        return ourInstanse;
    }

    public void register(String userName, String password) {
        Client client = Client.getInstance();
        client.addToSendingMessagesAndSend(
                Message.makeRegisterMessage(
                        client.getClientName(), Constants.SERVER_NAME, userName, password, 0)
        );
    }

    public void login(String userName, String password) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeLogInMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, userName, password, 0)
        );
    }
}
