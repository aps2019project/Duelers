package controller;

import models.account.AccountInfo;
import models.message.ChatMessage;
import models.message.DataName;
import models.message.Message;

import java.util.ArrayList;

import static models.Constants.SERVER_NAME;

public class MainMenuController {
    private static MainMenuController ourInstance;

    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private AccountInfo[] leaderBoard;


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

    synchronized void setLeaderBoard(AccountInfo[] leaderBoard) {
        this.leaderBoard = leaderBoard;
        this.notifyAll();
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }
}
