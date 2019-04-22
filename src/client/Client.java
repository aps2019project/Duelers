package client;

import client.models.account.Account;
import client.models.card.Card;
import client.models.card.Deck;
import client.models.game.Game;
import client.models.map.Position;
import client.models.menus.Menu;
import client.models.message.Message;
import com.google.gson.Gson;
import server.Server;

import java.util.ArrayList;

public class Client {
    private Server server;
    private String clientName;
    private Account account;
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();
    private Game game;
    private ArrayList<Deck> customDecks = new ArrayList<>();
    private ArrayList<Account> leaderBoard = new ArrayList<>();
    private Menu currentMenu;
    private Card selected;
    private ArrayList<Position> positions = new ArrayList<>();
    private boolean validation = true;
    private String errorMessage;
    public Client(String clientName, Menu currentMenu, Server server) {
        this.clientName = clientName;
        this.currentMenu = currentMenu;
        this.server = server;
    }

    public Account getAccount() {
        return account;
    }

    public void updateLeaderBoard(String serverName) {
        this.addToSendingMessages(Message.makeGetLeaderBoardMessage(clientName, serverName, 0));
        this.sendMessages();
    }


    public ArrayList<Account> getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(ArrayList<Account> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public String getClientName() {
        return clientName;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
        currentMenu.showHelp();
    }

    public void addToSendingMessages(Message message) {

    }

    public void addToReceivingMessages(String messageJson) {
        receivingMessages.add(Message.convertJsonToMessage(messageJson));
    }

    public void receiveMessages() {
        for (Message message : receivingMessages) {

        }
        receivingMessages.clear();
    }

    public void sendMessages() {
        for (Message message : sendingMessages) {
            server.addToReceivingMessages(message.toJson());
        }
        sendingMessages.clear();
        server.receiveMessages();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setCustomDecks(ArrayList<Deck> customDecks) {
        this.customDecks = customDecks;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }
}