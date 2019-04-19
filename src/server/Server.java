package server;

import client.Client;
import client.models.message.Message;
import com.google.gson.Gson;
import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Player;
import server.models.game.Story;
import server.models.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private static Server server;
    private String serverName;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Card> originalCards = new ArrayList<>();
    private HashMap<Account, Client> onlineClients = new HashMap<>();
    private ArrayList<Game> onlineGames = new ArrayList<>();
    private ArrayList<Deck> customDecks = new ArrayList<>();
    private ArrayList<Account> leaderBoard = new ArrayList<>();
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();
    private ArrayList<Story> stories = new ArrayList<>();

    private Server(String serverName) {
        readAccounts();
        readCustomDecks();
        readStories();
        this.serverName = serverName;
    }

    public static Server getInstance() {
        if (server == null)
            server = new Server("Server");
        return server;
    }

    public String getServerName() {
        return serverName;
    }

    public void addToSendingMessages(Message message) {
        sendingMessages.add(message);
    }

    public void addToReceivingMessages(String messageJson) {
        receivingMessages.add(Message.convertStringToMessage(messageJson));
    }

    public void receiveMessages() {
        for (Message message : receivingMessages) {

        }
        receivingMessages.clear();
    }

    public void sendMessages() {
        for (Message message : sendingMessages) {
            String json = new Gson().toJson(message);

        }
        sendingMessages.clear();
    }

    public void newGame(GameType gameType, Player playerOne, Player playerTwo, Map map) {
        onlineGames.add(new Game(gameType, playerOne, playerTwo, map));
        //...
    }

    private void readAccounts() {
        //file
    }

    private void readCustomDecks() {
        //file
    }

    private void readStories() {
        //file
    }
}