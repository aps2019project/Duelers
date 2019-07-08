package controller;

import javafx.application.Platform;
import models.Constants;
import models.message.ChatMessage;
import models.message.Message;
import view.GlobalChatDialog;
import view.MainMenu;

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

    public void addChatMessage(ChatMessage chatMessage) {
        Platform.runLater(() -> GlobalChatDialog.getInstance().addMessage(chatMessage));
    }

    public void sendChatMessage(String text) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeChatMessage(Client.getInstance().getClientName(),Constants.SERVER_NAME,Client.getInstance().getAccount().getUsername(),
                        null,text,0)
        );
    }
}
