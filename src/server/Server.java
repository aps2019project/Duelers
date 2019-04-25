package server;

import client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.models.account.Account;
import server.models.account.Collection;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Story;
import server.models.message.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final String accountsPath = "jsonData/accounts";
    private static final String[] cardPaths = {
            "jsonData/heroCards",
            "jsonData/minionCards",
            "jsonData/spellCards",
            "jsonData/itemCards/collectible",
            "jsonData/itemCards/usable"
    };
    private static final String flagPath = "jsonData/itemCards/flag/Flag.item.card.json";
    private static final String storiesPath = "jsonData/stories";

    private static Server server;
    private String serverName;
    private HashMap<Account, String> accounts = new HashMap<>();//Account -> ClientName
    private HashMap<String, Account> clients = new HashMap<>();//clientName -> Account
    private HashMap<Account, Game> onlineGames = new HashMap<>();//Account -> Game
    private ArrayList<Client> onlineClients = new ArrayList<>();
    private Collection originalCards = new Collection();
    private Card originalFlag;
    private ArrayList<Deck> customDecks = new ArrayList<>();
    private ArrayList<Story> stories = new ArrayList<>();
    private Account[] leaderBoard;
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();

    private Server(String serverName) {
        readAccounts();
        readOriginalCards();
        readCustomDecks();
        readStories();
        this.serverName = serverName;
        serverPrint("Server Was Created!");
    }

    public static Server getInstance() {
        if (server == null)
            server = new Server("Server");
        return server;
    }

    public void addClient(Client client) {
        if (client == null || client.getClientName() == null) {
            serverPrint("Invalid Client Was Not Added.");
        } else if (clients.containsKey(client.getClientName())) {
            serverPrint("Client Name Was Duplicate.");
        } else {
            onlineClients.add(client);
            clients.put(client.getClientName(), null);
            serverPrint("Client:" + client.getClientName() + " Was Added!");
        }
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
                serverPrint("Message's Receiver Was Not This Server.");
                continue;
            }
            switch (message.getMessageType()) {
                case REGISTER:
                    register(message);
                    break;
                case LOG_IN:
                    logIn(message);
                    break;
                case LOG_OUT:
                    logOut(message);
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
        sendMessages();
    }

    private void sendMessages() {
        for (Message message : sendingMessages) {
            Client client = getClient(message.getReceiver());
            if (client == null) {
                serverPrint("Message's Client Was Not Found.");
                continue;
            }
            client.addToReceivingMessages(message.toJson());

        }

        sendingMessages.clear();
    }

    private Account getAccount(String userName) {
        if (userName == null) {
            serverPrint("Invalid UserName In getAccount.");
            return null;
        }
        for (Map.Entry<Account, String> map : accounts.entrySet()) {
            if (map.getKey().getUsername().equals(userName)) {
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
        if (getAccount(message.getUserName()) != null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "invalid userName", message.getMessageId()));
        } else if (message.getPassWord() == null || message.getPassWord().length() < 4) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "invalid passWord", message.getMessageId()));
        } else {
            Account account = new Account(message.getUserName(), message.getPassWord());
            accounts.put(account, null);
            onlineGames.put(account, null);
            saveAccount(account);
            serverPrint(message.getUserName() + " Is Created!");
            logIn(message);
        }

    }

    private void logIn(Message message) {
        Account account = getAccount(message.getUserName());
        Client client = getClient(message.getSender());
        if (client == null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not added", message.getMessageId()));
        } else if (account == null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "username not found", message.getMessageId()));
        } else if (!account.getPassword().equals(message.getPassWord())) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "incorrect password", message.getMessageId()));
        } else if (accounts.get(account) != null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "online account", message.getMessageId()));
        } else if (clients.get(message.getSender()) != null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was logged in", message.getMessageId()));
        } else {
            accounts.replace(account, message.getSender());
            clients.replace(message.getSender(), account);
            addToSendingMessages(Message.makeAccountCopyMessage(
                    serverName, message.getSender(), account, message.getMessageId()));
            serverPrint(message.getSender() + " Is Logged In");
        }
    }

    private boolean preCheckMessage(Message message) {
        if (!clients.containsKey(message.getSender())) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not added", message.getMessageId()));
            return false;
        } else if (clients.get(message.getSender()) == null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not logged in", message.getMessageId()));
            return false;
        } else {
            return true;
        }
    }

    private void logOut(Message message) {
        if (preCheckMessage(message)) {
            accounts.replace(clients.get(message.getSender()), null);
            clients.replace(message.getSender(), null);
            serverPrint(message.getSender() + " Is Logged Out.");
        }
    }

    private void createDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (account.getDeck(message.getDeckName()) == null) {
                account.getDecks().add(new Deck(message.getDeckName()));
            } else {
                addToSendingMessages(Message.makeExceptionMessage(
                        serverName, message.getSender(), "deck's name was duplicate.", message.getMessageId()));
            }
        }
    }

    private void removeDeck(Message message) {

    }

    private void addToDeck(Message message) {

    }

    private void removeFromDeck(Message message) {

    }

    private void selectDeck(Message message) {

    }

    private void buyCard(Message message) {

    }

    private void sellCard(Message message) {

    }

    private void saveChanges(Message message) {
        if (preCheckMessage(message)) {
            saveAccount(clients.get(message.getSender()));
        }
    }

    public void newGame(Account account1, Account account2, GameType gameType) {

    }

    private void readAccounts() {
        File[] files = new File(accountsPath).listFiles();
        if (files != null) {
            for (File file : files) {
                Account account = loadFile(file, Account.class);
                if (account == null) continue;

                accounts.put(account, null);
            }
        }
        serverPrint("Accounts loaded");
    }

    private void readOriginalCards() {
        for (String path : cardPaths) {
            File[] files = new File(path).listFiles();
            if (files != null) {
                for (File file : files) {
                    Card card = loadFile(file, Card.class);
                    if (card == null) continue;

                    originalCards.addCard(card);
                }
            }
        }
        originalFlag = loadFile(new File(flagPath), Card.class);
        serverPrint("Original Cards loaded");
    }

    private void readCustomDecks() {
        //file
    }

    private void readStories() {
        File[] files = new File(storiesPath).listFiles();
        if (files != null) {
            for (File file : files) {
                Story story = loadFile(file, Story.class);
                if (story == null) continue;

                stories.add(story);
            }
        }
        serverPrint("Stories loaded");
    }

    private <T> T loadFile(File file, Class<T> classOfT) {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(file)), classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveAccount(Account account) {
        String accountJson = new GsonBuilder().setPrettyPrinting().create().toJson(account);

        try {
            FileWriter writer = new FileWriter("jsonData/accounts/" + account.getUsername() + ".account.json");
            writer.write(accountJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerName() {
        return serverName;
    }

    private void serverPrint(String string) {
        System.out.println("\u001B[31m" + string.trim() + "\u001B[0m");
    }
}