package controller;

import com.google.gson.Gson;
import javafx.application.Platform;
import models.Constants;
import models.account.Account;
import models.account.AccountInfo;
import models.card.Card;
import models.card.DeckInfo;
import models.game.map.Position;
import models.message.Message;
import view.MainMenu;
import view.Show;

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
    private Show currentShow;
    private LinkedList<Message> receivingMessages = new LinkedList<>();
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private Card selected;
    private Position[] positions;
    private boolean validation = true;
    private String errorMessage;
    private int lastSentMessageId = 0;
    private int lastReceivedMessageId = 0;
    private Socket socket;
    private Gson gson = new Gson();
    private Thread sendMessageThread;
    private Thread receiveMessageThread;
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
        socket = getSocketReady();
        sendClientNameToServer(socket);
        sendMessageThread = new Thread(() -> {
            try {
                sendMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendMessageThread.start();
        receiveMessages();
    }

    private void sendClientNameToServer(Socket socket) throws IOException {
        while (!bufferedReader.readLine().equals("#Listening#")) ;
        System.out.println("server is listening to me");

        clientName = InetAddress.getLocalHost().getHostName();
        socket.getOutputStream().write(("#" + clientName + "#\n").getBytes());
        int x = 1;
        while (!bufferedReader.readLine().equals("#Valid#")) {
            clientName = String.format("%s%d", clientName, x);
            x++;
            socket.getOutputStream().write(("#" + clientName + "#\n").getBytes());
        }
        System.out.println("server accepted me.");
    }

    private Socket getSocketReady() throws IOException {
        Socket socket = new Socket(Constants.SERVER_IP, Constants.PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("network connected.");

        return socket;
    }

    public void addToSendingMessagesAndSend(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
            sendingMessages.notify();
        }
    }

    private void sendMessages() throws IOException {
        System.out.println("sending messages started");
        while (true) {
            Message message;
            synchronized (sendingMessages) {
                message = sendingMessages.poll();
            }
            if (message != null) {
                String json = message.toJson();
                socket.getOutputStream().write((json + "\n").getBytes());

                System.out.println("message sent: " + json);
            } else {
                try {
                    synchronized (sendingMessages) {
                        sendingMessages.wait();
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private void receiveMessages() throws IOException {
        System.out.println("receiving messages started.");
        while (true) {
            String json = bufferedReader.readLine();
            Message message = gson.fromJson(json, Message.class);

            System.out.println("message received: " + json);

            handleMessage(message);
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
        Platform.runLater(() -> currentShow.showError(message.getExceptionMessage().getExceptionString()));
    }

    private void login(Message message) {
        account = new Account(message.getAccountCopyMessage().getAccount());
        Platform.runLater(() -> new MainMenu().show());
    }

    public void disconnected() {
    }


    public String getClientName() {
        return clientName;
    }


    public Account getAccount() {
        return account;
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println("socket closed");
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Show getCurrentShow() {
        return currentShow;
    }

    public void setShow(Show show) {
        this.currentShow = show;
    }

    public void makeConnection() {
        new Thread(() -> {
            try {
                connect();
            } catch (IOException e) {
                Platform.runLater(() ->
                        currentShow.showError("Connection failed", "RETRY", event -> makeConnection())
                );
                disconnected();
            }
        }).start();
    }
}
