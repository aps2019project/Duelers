package server;

import client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.models.RequestType;
import server.models.account.Account;
import server.models.account.Collection;
import server.models.account.TempAccount;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.Story;
import server.models.game.TempStory;
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
    private static final String ACCOUNTS_PATH = "jsonData/accounts";
    private static final String[] CARDS_PATHS = {
            "jsonData/heroCards",
            "jsonData/minionCards",
            "jsonData/spellCards",
            "jsonData/itemCards/collectible",
            "jsonData/itemCards/usable"
    };
    private static final String FLAG_PATH = "jsonData/itemCards/flag/Flag.item.card.json";
    private static final String STORIES_PATH = "jsonData/stories";

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
                    login(message);
                    break;
                case LOG_OUT:
                    logout(message);
                    break;
                case GET_LEADERBOARD:
                    sendLeaderBoard(message);
                    break;
                case GET_ORIGINAL_CARDS:
                    sendOriginalCards(message);
                    break;
                case GET_STORIES:
                    sendStories(message);
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
        if (getAccount(message.getUsername()) != null) {
            sendException("Invalid Username!", message.getSender(), message.getMessageId());
        } else if (message.getPassword() == null || message.getPassword().length() < 4) {
            sendException("Invalid Password!", message.getSender(), message.getMessageId());
        } else {
            Account account = new Account(message.getUsername(), message.getPassword());
            accounts.put(account, null);
            onlineGames.put(account, null);
            saveAccount(account);
            serverPrint(message.getUsername() + " Is Created!");
            login(message);
        }
    }

    private void login(Message message) {
        Account account = getAccount(message.getUsername());
        Client client = getClient(message.getSender());
        if (client == null) {
            serverPrint("Client Wasn't Added!");
            sendException("Client Wasn't Added!", message.getSender(), message.getMessageId());
        } else if (account == null) {
            sendException("Username Not Found!", message.getSender(), message.getMessageId());
        } else if (!account.getPassword().equals(message.getPassword())) {
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

    private void logout(Message message) {
        if (preCheckMessage(message)) {
            accounts.replace(clients.get(message.getSender()), null);
            clients.replace(message.getSender(), null);
            serverPrint(message.getSender() + " Is Logged Out.");
        }
    }

    private void createDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                account.addDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            } else {
                addToSendingMessages(Message.makeExceptionMessage(
                        serverName, message.getSender(), "deck's name was duplicate.", message.getMessageId()));
            }
        }
    }

    private void removeDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (account.hasDeck(message.getDeckName())) {
                account.deleteDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            } else {
                addToSendingMessages(Message.makeExceptionMessage(
                        serverName, message.getSender(), "deck was not found.", message.getMessageId()));
            }
        }
    }

    private void addToDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found.", message.getSender(), message.getMessageId());
            } else if (!account.getCollection().hasCard(message.getCardIds()[0])) {
                sendException("invalid cardid.", message.getSender(), message.getMessageId());
            } else if(account.getDeck(message.getDeckName()).hasCard(message.getCardIds()[0])) {
                sendException("deck had this card.", message.getSender(), message.getMessageId());
            } else{
                account.addCardToDeck(message.getCardIds()[0], message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            }
        }
    }

    private void removeFromDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found.", message.getSender(), message.getMessageId());
            } else if (!account.getDeck(message.getDeckName()).hasCard(message.getCardIds()[0])) {
                sendException("deck didn't have this card.", message.getSender(), message.getMessageId());
            } else {
                account.removeCardFromDeck(message.getCardIds()[0], message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            }
        }
    }

    private void selectDeck(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found", message.getSender(), message.getMessageId());

            } else if (account.getMainDeck() != null && account.getMainDeck().getDeckName().equals(message.getDeckName())) {
                sendException("deck was already the main deck.", message.getSender(), message.getMessageId());

            } else {
                account.selectDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            }
        }
    }

    private void buyCard(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!originalCards.hasCard(message.getCardName())) {
                sendException("invalid card name", message.getSender(), message.getMessageId());
            } else if (account.getMoney() < originalCards.getCard(message.getCardName()).getPrice()) {
                sendException("account's money isn't enough.", message.getSender(), message.getMessageId());
            } else {
                account.buyCard(message.getCardName(), originalCards.getCard(message.getCardName()).getPrice(), originalCards);
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            }
        }
    }

    private void sellCard(Message message) {
        if (preCheckMessage(message)) {
            Account account = clients.get(message.getSender());
            if (!account.getCollection().hasCard(message.getCardName())) {
                sendException("invalid card name", message.getSender(), message.getMessageId());
            } else {
                account.sellCard(message.getCardName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
            }
        }
    }

    private void sendStories(Message message) {
        if (preCheckMessage(message)) {
            addToSendingMessages(Message.makeStoriesCopyMessage
                    (serverName, message.getSender(), stories.toArray(Story[]::new), message.getMessageId()));
        }
    }

    private void sendOriginalCards(Message message) {
        if (preCheckMessage(message)) {
            addToSendingMessages(Message.makeOriginalCardsCopyMessage(
                    serverName, message.getSender(), originalCards, message.getMessageId()));
        }
    }

    private void sendLeaderBoard(Message message) {
        if (accounts.size() == 0) {
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
        String command = message.getSudoCommand().toLowerCase();
        /*if (RequestType.SHOW_ACCOUNTS.matches(command)) {
            for (Map.Entry<Account, String> account : accounts.entrySet()) {
                serverPrint(account.getKey().getUsername() + " " + account.getKey().getPassword());
            }
        }*/
        if (command.contains("account")) {
            for (Map.Entry<Account, String> account : accounts.entrySet()) {
                serverPrint(account.getKey().getUsername() + " " + account.getKey().getPassword());
            }
        }

    }

    private void readAccounts() {
        File[] files = new File(ACCOUNTS_PATH).listFiles();
        if (files != null) {
            for (File file : files) {
                TempAccount account = loadFile(file, TempAccount.class);
                if (account == null) continue;

                accounts.put(new Account(account), null);
            }
        }
        serverPrint("Accounts loaded");
    }

    private void readOriginalCards() {
        for (String path : CARDS_PATHS) {
            File[] files = new File(path).listFiles();
            if (files != null) {
                for (File file : files) {
                    Card card = loadFile(file, Card.class);
                    if (card == null) continue;

                    originalCards.addCard(card);
                }
            }
        }
        originalFlag = loadFile(new File(FLAG_PATH), Card.class);
        serverPrint("Original Cards loaded");
    }

    private void readStories() {
        File[] files = new File(STORIES_PATH).listFiles();
        if (files != null) {
            for (File file : files) {
                TempStory story = loadFile(file, TempStory.class);
                if (story == null) continue;

                stories.add(new Story(story, originalCards));
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
        String accountJson = new GsonBuilder().setPrettyPrinting().create().toJson(new TempAccount(account));

        try {
            FileWriter writer = new FileWriter(ACCOUNTS_PATH + "/" + account.getUsername() + ".account.json");
            writer.write(accountJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void serverPrint(String string) {
        System.out.println("\u001B[31m" + string.trim() + "\u001B[0m");
    }
}