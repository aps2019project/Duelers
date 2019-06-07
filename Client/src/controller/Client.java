package controller;

import com.google.gson.Gson;
import models.Constants;
import models.account.Account;
import models.account.AccountInfo;
import models.card.Card;
import models.card.DeckInfo;
import models.game.map.Position;
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

    public void addToSendingMessages(Message message) {
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
                        socket.getOutputStream().write(gson.toJson(message).getBytes());
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

    public void disconnected() {
    }


    private void connectToServer() throws IOException {
        socket = new Socket(Constants.SERVER_IP, Constants.PORT);
    }

    public String getClientName() {
        return clientName;
    }
}
