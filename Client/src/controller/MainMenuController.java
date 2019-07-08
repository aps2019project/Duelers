package controller;


import javafx.application.Platform;
import models.Constants;
import models.account.AccountInfo;
import models.account.AccountType;
import models.account.Collection;
import models.message.ChatMessage;
import models.message.DataName;
import models.message.Message;
import view.GlobalChatDialog;
import view.MainMenu;

import java.util.ArrayList;

import static models.Constants.SERVER_NAME;

public class MainMenuController {
    private static MainMenuController ourInstance;
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private AccountInfo[] leaderBoard;
    private Collection customCardRequests;


    public static MainMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new MainMenuController();
        }
        return ourInstance;
    }

    public void logout() {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeLogOutMessage(
                        Client.getInstance().getClientName(), SERVER_NAME, 0
                )
        );
    }

    public void requestLeaderboard() {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeGetDataMessage(
                        Client.getInstance().getClientName(), SERVER_NAME, DataName.LEADERBOARD, 0
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

    synchronized void setLeaderBoard(AccountInfo[] leaderBoard) {
        this.leaderBoard = leaderBoard;
        this.notifyAll();
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }

    public void changeAccountTypeRequest(String username, AccountType newValue) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeChangeAccountTypeMessage(Client.getInstance().getClientName(), SERVER_NAME, username, newValue)
        );
    }

    public void requestCustomCardRequests() {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeGetDataMessage(Client.getInstance().getClientName(),SERVER_NAME,DataName.CUSTOM_CARDS,0)
        );
    }

    public void acceptCustomCard(String cardName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeValidateCustomCardMessage(Client.getInstance().getClientName(),SERVER_NAME,cardName,0)
        );
    }

    public void rejectCustomCard(String cardName) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeInValidateCustomCardMessage(Client.getInstance().getClientName(),SERVER_NAME,cardName,0)
        );
    }

    synchronized void setCustomCardRequests(Collection customCardRequests) {
        this.customCardRequests = customCardRequests;
        this.notifyAll();
    }

    public Collection getCustomCardRequests() {
        return customCardRequests;
    }
}
