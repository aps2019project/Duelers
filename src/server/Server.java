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
    private HashMap<Account, Game> onlineGames = new HashMap<>();//Account -> Game
    private ArrayList<Client> onlineClients = new ArrayList<>();
    private ArrayList<Card> originalCards = new ArrayList<>();
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
            serverPrint("Client:"+client.getClientName()+" Was Addad!");
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
        if (getAccount(message.getUserName()) != null) {
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "invalid userName", message.getMessageId()));
        } else if(message.getPassWord()==null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "invalid passWord", message.getMessageId()));
        }else{
            Account account=new Account(message.getUserName(),message.getPassWord());
            accounts.put(account,null);
            onlineGames.put(account,null);
            saveAccount(account);
        }
    }

    private void logIn(Message message){
        Account account=getAccount(message.getUserName());
        Client client=getClient(message.getSender());
        if(client==null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not added", message.getMessageId()));
        }else if(account==null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "username not found", message.getMessageId()));
        }else if(!account.getPassWord().equals(message.getPassWord())){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "incorrect password", message.getMessageId()));
        }else if(accounts.get(account)!=null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "online account", message.getMessageId()));
        }else if(clients.get(message.getSender())!=null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was logged in", message.getMessageId()));
        }else{
            accounts.replace(account,null,message.getSender());
            clients.replace(message.getSender(),null,account);
            addToSendingMessages(Message.makeAccountCopyMessage(
                    serverName,message.getSender(),account,message.getMessageId()));
        }
    }

    private void logOut(Message message){
        Client client=getClient(message.getSender());
        Account account=clients.get(message.getSender());
        if(client==null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not added", message.getMessageId()));
        }else if(account==null){
            addToSendingMessages(Message.makeExceptionMessage(
                    serverName, message.getSender(), "client was not logged in", message.getMessageId()));
        }else{
            accounts.replace(account,message.getSender(),null);
            clients.replace(message.getSender(),account,null);
        }

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

    private void saveAccount(Account account){
        //file
    }

    private void logIn(Client client, String userName, String passWord) {

    }

    public String getServerName() {
        return serverName;
    }

    private void serverPrint(String string){
        System.out.println("\u001B[31m" + string.trim() + "\u001B[0m");
    }
}