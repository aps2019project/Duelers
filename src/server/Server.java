package server;

import client.Client;
import server.models.message.Message;
import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Story;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Server server;
    private String serverName;
    private HashMap<Account, String> accounts = new HashMap<>();//Account -> ClientName
    private HashMap<String, Account> clients = new HashMap<>();//clientName -> Account
    private ArrayList<Client> onlineClients = new ArrayList<>();
    private ArrayList<Card> originalCards = new ArrayList<>();
    private ArrayList<Deck> customDecks = new ArrayList<>();
    private ArrayList<Story> stories = new ArrayList<>();
    private Account[] leaderBoard;
    private HashMap<Account, Game> onlineGames = new HashMap<>();//Account -> Game
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();

    private Server(String serverName) {
        readAccounts();
        readOriginalCards();
        readCustomDecks();
        readStories();
        this.serverName = serverName;
        System.out.println("--Server was created.");
    }

    public static Server getInstance() {
        if (server == null)
            server = new Server("Server");
        return server;
    }

    public void addClient(Client client) {
        if (client == null || client.getClientName() == null) {
            System.out.println("Error addClient NULL");
            return;
        }
        if (clients.containsKey(client.getClientName())) {
            System.out.println("Error addClient duplicate");
            return;
        }
        onlineClients.add(client);
        clients.put(client.getClientName(), null);
    }

    private void addToSendingMessages(Message message) {
        sendingMessages.add(message);
    }

    public void addToReceivingMessages(String messageJson) {
        receivingMessages.add(Message.convertJsonToMessage(messageJson));
    }

    public void receiveMessages() {
        for (Message message : receivingMessages) {
            if (!message.getReceiver().equals(serverName)) {
                System.out.println("Error receive message");
                continue;
            }
            switch (message.getMessageType()) {
                case REGISTER:

                    break;
                case LOG_IN:

                    break;
                case LOG_OUT:

                    break;
                case GET_LEADERBOARD:

                    break;
                case GET_ORIGINAL_CARDS:

                    break;
                case BUY_CARD:

                    break;
                case SELL_CARD:

                    break;
                case SAVE_CHANGES:

                    break;
                case CREATE_DECK:

                    break;
                case REMOVE_DECK:

                    break;
                case ADD_TO_DECK:

                    break;
                case REMOVE_FROM_DECK:

                    break;
                case SELECT_DECK:

                    break;
                case NEW_GAME:

                    break;
                case INSERT:

                    break;
                case ATTACK:

                    break;
                case END_TURN:

                    break;
                case COMBO:

                    break;
                case USE_SPELL:

                    break;
                case MOVE_TROOP:

                    break;
                default:
                    addToSendingMessages(Message.makeExceptionMessage
                            (serverName, message.getSender(), "messageType", message.getMessageId()));
                    sendMessages();
                    break;
            }
        }
        receivingMessages.clear();
    }

    public void sendMessages() {
        for (Message message : sendingMessages) {
            Client client = getClient(message.getReceiver());
            if (client == null || !message.getSender().equals(serverName)) {
                System.out.println("Error sending messages");
                continue;
            }
            client.addToReceivingMessages(message.toJson());
        }
        sendingMessages.clear();
    }

    private Account getAccount(String userName) {
        for (Map.Entry<Account, String> map : accounts.entrySet()) {
            if (map.getKey().getUserName().equals(userName)) {
                return map.getKey();
            }
        }
        return null;
    }

    private Client getClient(String clientName) {
        for (Client client : onlineClients) {
            if (client.getClientName().equals(clientName))
                return client;
        }
        return null;
    }

    private void register(Message message) {

    }

    public void newGame(Account account1, Account account2, GameType gameType) {

    }

    private void readAccounts() {
        //file
    }

    private void readOriginalCards() {
        //file
    }

    private void readCustomDecks() {
        //file
    }

    private void readStories() {
        //file
    }

    private void logIn(Client client, String userName, String passWord) {

    }

    public String getServerName() {
        return serverName;
    }
}