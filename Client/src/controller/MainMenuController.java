package controller;

import models.Constants;
import models.message.ChatMessage;
import models.message.Message;

import java.util.ArrayList;

public class MainMenuController {
    private static MainMenuController ourInstance;

    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();


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
}
