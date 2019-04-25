package server;

import client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.models.account.Account;
import server.models.account.Collection;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.Story;
import server.models.message.Message;
import server.models.sorter.LeaderBoardSorter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (client == null || client.getClientName().length() < 2) {
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
                    sendLeaderBoard(message);
                    break;
                case GET_ORIGINAL_CARDS:
                    sendOriginalCards(message);
                    break;
                case BUY_CARD:
                    buyCard(message);
                    break;
                case SELL_CARD:
                    sellCard(message);
                    break;
                case SAVE_CHANGES:
                    saveChanges(message);
                    break;
                case CREATE_DECK:
                    createDeck(message);
                    break;
                case REMOVE_DECK:
                    removeDeck(message);
                    break;
                case ADD_TO_DECK:
                    addToDeck(message);
                    break;
                case REMOVE_FROM_DECK:
                    removeFromDeck(message);
                    break;
                case SELECT_DECK:
                    selectDeck(message);
                    break;
                case NEW_GAME:
                    newGame(message);
                    break;
                case INSERT:
                    insertCard(message);
                    break;
                case ATTACK:
                    attack(message);
                    break;
                case END_TURN:
                    endTurn(message);
                    break;
                case COMBO:
                    combo(message);
                    break;
                case USE_SPELL:
                    useSpell(message);
                    break;
                case MOVE_TROOP:
                    moveTroop(message);
                    break;
                case SUDO:
                    sudo(message);
                    break;
                default:
                    sendException("Invalid Message Type!", message.getSender(), message.getMessageId());
                    serverPrint("Invalid Message Type!");
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
            serverPrint("Null UserName In getAccount.");
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
        if (clientName == null) {
            serverPrint("Null ClientName In getClient.");
            return null;
        }
        for (Client client : onlineClients) {
            if (client.getClientName().equals(clientName))
                return client;
        }
        return null;
    }

    private void sendException(String exceptionString, String receiver, int messageId) {
        addToSendingMessages(Message.makeExceptionMessage(
                serverName, receiver, exceptionString, messageId));
    }

    private void register(Message message) {
        if (getAccount(message.getUserName()) != null) {
            sendException("Invalid UserName!", message.getSender(), message.getMessageId());
        } else if (message.getPassWord() == null || message.getPassWord().length() < 4) {
            sendException("Invalid PassWord!", message.getSender(), message.getMessageId());
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
            serverPrint("Client Wasn't Added!");
            sendException("Client Wasn't Added!", message.getSender(), message.getMessageId());
        } else if (account == null) {
            sendException("UserName Not Found!", message.getSender(), message.getMessageId());
        } else if (!account.getPassword().equals(message.getPassWord())) {
            sendException("Incorrect PassWord!", message.getSender(), message.getMessageId());
        } else if (accounts.get(account) != null) {
            sendException("Online Account!", message.getSender(), message.getMessageId());
        } else if (clients.get(message.getSender()) != null) {
            sendException("Client Was Logged In!", message.getSender(), message.getMessageId());
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
            serverPrint("Client Wasn't Added!");
            sendException("Client Wasn't Added!", message.getSender(), message.getMessageId());
            return false;
        } else if (clients.get(message.getSender()) == null) {
            sendException("Client Was Not LoggedIn", message.getSender(), message.getMessageId());
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

    private void sendOriginalCards(Message message) {

    }

    private void sendLeaderBoard(Message message) {
        if (accounts.size()==0) {
            addToSendingMessages(Message.makeExceptionMessage(serverName, message.getSender(), "leader board is empty", 0));
            sendMessages();
        }
        leaderBoard = new Account[accounts.size()];
        int counter = 0;
        for (Account account : accounts.keySet()) {
            leaderBoard[counter] = account;
            counter++;
        }
        Arrays.sort(leaderBoard, new LeaderBoardSorter());
        addToSendingMessages(Message.makeLeaderBoardCopyMessage(serverName, message.getSender(), leaderBoard, 0));
        sendMessages();
    }

    private void insertCard(Message message) {

    }

    private void attack(Message message) {

    }

    private void combo(Message message) {

    }

    private void useSpell(Message message) {

    }

    private void moveTroop(Message message) {

    }

    private void endTurn(Message message) {

    }

    private void saveChanges(Message message) {
        if (preCheckMessage(message)) {
            saveAccount(clients.get(message.getSender()));
        }
    }

    private void newGame(Message message) {

    }

    private void sudo(Message message) {

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