package controller;

import com.google.gson.Gson;
import models.Constants;
import models.JsonConverter;
import models.account.Account;
import models.account.AccountInfo;
import models.card.Card;
import models.card.DeckInfo;
import models.game.map.Position;
import models.message.CardPosition;
import models.message.GameUpdateMessage;
import models.message.Message;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class Client {
    private static Client client;
    private final LinkedList<Message> sendingMessages = new LinkedList<>();
    private String clientName;
    private Account account;
    private LinkedList<Message> receivingMessages = new LinkedList<>();
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private Menu currentMenu;
    private Card selected;
    private Position[] positions;
    private boolean validation = true;
    private String errorMessage;
    private int lastSentMessageId = 0;
    private int lastReceivedMessageId = 0;
    private Socket socket;
    private Gson gson = new Gson();
    private Thread sendMessageThread;
    private Thread recieveMessageThread;
    private BufferedReader bufferedReader;

    private Client() {
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public void connect() throws IOException {
        Socket socket = getSocketReady();
        sendClientNameToServer(socket);
        sendMessageThread = new Thread(this::sendMessages);
        recieveMessageThread = new Thread(this::receiveMessages);
        //TODO:show AccountMenu
    }

    private void sendClientNameToServer(Socket socket) throws IOException {
        while (!bufferedReader.readLine().equals("#Listening#")) ;
        clientName = InetAddress.getLocalHost().getHostName();
        socket.getOutputStream().write(clientName.getBytes());
        int x = 1;
        while (!bufferedReader.readLine().equals("#Valid#")) {
            clientName = String.format("%s%d", clientName, x);
            x++;
            socket.getOutputStream().write(String.format("#%s#", clientName).getBytes());
        }
    }

    private Socket getSocketReady() throws IOException {
        Socket socket = new Socket(Constants.SERVER_IP, Constants.PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return socket;
    }

    public void addToSendingMessagesAndSend(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
            sendingMessages.notify();
        }
    }

    private void sendMessages() {
        while (true) {
            synchronized (sendingMessages) {
                for (Message message : sendingMessages) {
                    try {
                        socket.getOutputStream().write(message.toJson().getBytes());
                    } catch (IOException e) {
                        disconnected();
                    }
                }
                try {
                    sendingMessages.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void receiveMessages() {
        while (true) {
            try {
                Message message = gson.fromJson(bufferedReader.readLine(), Message.class);
                handleMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(Message message) {
        if (message.getMessageId() > lastReceivedMessageId)
            lastReceivedMessageId = message.getMessageId();
        switch (message.getMessageType()) {
            case SEND_EXCEPTION:
                showError(message);
                break;
            case ACCOUNT_COPY:
                login(message);
                break;
            case GAME_COPY:
                GameController.getInstance().setCurrentGame(message.getGameCopyMessage().getCompressedGame());
                GameController.getInstance().calculateAvailableActions();
                //TODO:not completed
                break;
            case ORIGINAL_CARDS_COPY:
                ShopController.getInstance().setOriginalCards(message.getOriginalCardsCopyMessage().getOriginalCards());
                break;
            case LEADERBOARD_COPY:
                leaderBoard = message.getLeaderBoardCopyMessage().getLeaderBoard();
                break;
            case STORIES_COPY:
                StoryMenuController.getInstance().setStories(message.getStoriesCopyMessage().getStories());
                break;
//            case OPPONENT_INFO:
//                MultiPlayerMenu.getInstance().setSecondAccount(message.getOpponentInfoMessage().getOpponentInfo());
//                break;
//            case CARD_POSITION://TODO:CHANGE
//                CardPosition cardPosition = message.getCardPositionMessage().getCardPosition();
//                switch (cardPosition) {
//                    case MAP:
//                        gameCommands.getCurrentGame().moveCardToMap(message.getCardPositionMessage().getCompressedCard());
//                        GameCommands.getInstance().calculateAvailableActions();
//                        break;
//                    case HAND:
//                        gameCommands.getCurrentGame().moveCardToHand();
//                        GameCommands.getInstance().calculateAvailableActions();
//                        break;
//                    case NEXT:
//                        gameCommands.getCurrentGame().moveCardToNext(message.getCardPositionMessage().getCompressedCard());
//                        GameCommands.getInstance().calculateAvailableActions();
//                        break;
//                    case GRAVE_YARD:
//                        gameCommands.getCurrentGame().moveCardToGraveYard(message.getCardPositionMessage().getCompressedCard());
//                        GameCommands.getInstance().calculateAvailableActions();
//                        break;
//                    case COLLECTED:
//                        gameCommands.getCurrentGame().moveCardToCollectedItems(message.getCardPositionMessage().getCompressedCard());
//                        GameCommands.getInstance().calculateAvailableActions();
//                        break;
//                }
//                break;
//            case TROOP_UPDATE:
//                GameController.getInstance().getCurrentGame().troopUpdate(message.getTroopUpdateMessage().getCompressedTroop());
//                gameCommands.calculateAvailableActions();
//                break;
//            case GAME_UPDATE:
//                GameUpdateMessage gameUpdateMessage = message.getGameUpdateMessage();
//                GameController.getInstance().getCurrentGame().gameUpdate(
//                        gameUpdateMessage.getTurnNumber(),
//                        gameUpdateMessage.getPlayer1CurrentMP(),
//                        gameUpdateMessage.getPlayer1NumberOfCollectedFlags(),
//                        gameUpdateMessage.getPlayer2CurrentMP(),
//                        gameUpdateMessage.getPlayer2NumberOfCollectedFlags());
//                GameCommands.getInstance().calculateAvailableActions();
//                break;
//            case Game_FINISH:
//                GameResultStatus.getInstance().setWinner(message.getGameFinishMessage().amIWinner());
//                setCurrentMenu(GameResultStatus.getInstance());
//                break;
//            case DONE:
//                nothing/just update last received message id
//                break;
        }
    }

    private void showError(Message message) {
        validation = false;
        errorMessage = message.getExceptionMessage().getExceptionString();
        //TODO: graphic show error
    }

    private void login(Message message) {
        account = new Account(message.getAccountCopyMessage().getAccount());
        //TODO:change scene from login menu to main
    }

    public void disconnected() {
    }


    public String getClientName() {
        return clientName;
    }


    public Account getAccount() {
        return account;
    }
}
