package controller;

import models.Constants;
import models.message.Message;

public class AccountMenuController {
    private static AccountMenuController ourInstanse;

    public static AccountMenuController getInstance() {
        if (ourInstanse == null) {
            ourInstanse = new AccountMenuController();
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
