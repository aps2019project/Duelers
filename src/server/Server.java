package server;

import client.Client;
import org.jetbrains.annotations.NotNull;
import server.models.message.Message;
import com.google.gson.Gson;
import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Story;

import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private static Server server;
    private String serverName;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Card> originalCards = new ArrayList<>();
    private HashMap<Client, Account> onlineClients = new HashMap<>();
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

    public void addClient(@NotNull Client client) {
        if (getClient(client.getClientName()) == null)
            onlineClients.put(client, null);
        else
            System.out.println("client was duplicate!");
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
            Client client = getClient(message.getSender());
            if (client == null) {
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
                case GET_ORIGINALCARDS:

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
                    if(message.getOpponentUserName()!=null){
                        Account account1=onlineClients.get(client);
                        Account account2=getAccount(message.getUserName());
                        if(account1!=null && account2!=null){
                            newGame(account1,account2,message.getGameType());
                        }
                    }
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
            if(client!=null){
                client.addToReceivingMessages(new Gson().toJson(message));
                client.receiveMessages();
            }
        }
        sendingMessages.clear();
    }

    private Account getAccount(String userName){
        for(Account account:accounts){
            if(account.getUserName().equals(userName)){
                return account;
            }
        }
        System.out.println("account not found");
        return null;
    }

    private Client getClient(Account account) {
        for (java.util.Map.Entry<Client, Account> map : onlineClients.entrySet()) {
            if (map.getValue()==account) {
                return map.getKey();
            }
        }
        System.out.println("account not loged in");
        return null;
    }

    private Client getClient(String clientName) {
        for (java.util.Map.Entry<Client, Account> map : onlineClients.entrySet()) {
            if (map.getKey().getClientName().equals(clientName)) {
                return map.getKey();
            }
        }
        System.out.println("Client not found");
        return null;
    }


    public void newGame(@NotNull Account account1,@NotNull Account account2,GameType gameType) {

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

    private void logIn(Client client, String userName, String passWord) {

    }
}