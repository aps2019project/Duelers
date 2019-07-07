package controller;

import models.Constants;
import models.message.ChatMessage;
import models.message.Message;

public class MainMenuController {
    private static MainMenuController ourInstance;

    public static MainMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new MainMenuController();
        }
        return ourInstance;
    }

    public void logout() {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeLogOutMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, 0
                )
        );
    }

    public void addChatMessage(ChatMessage chatMessage) {

    }
}
