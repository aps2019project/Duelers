package client;

import client.models.account.Account;
import client.models.account.AccountInfo;
import client.models.account.Collection;
import client.models.card.Card;
import client.models.card.DeckInfo;
import client.models.game.Game;
import client.models.map.Position;
import client.models.menus.AccountMenu;
import client.models.menus.Menu;
import client.models.message.Message;
import server.Server;

import java.util.ArrayList;

public class Client {
    private Server server;
    private String clientName;
    private Account account;
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();
    private Game game;
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private Menu currentMenu;
    private Card selected;
    private ArrayList<Position> positions = new ArrayList<>();
    private boolean validation = true;
    private String errorMessage;
    private Collection originalCards = new Collection();

    public Client(String clientName, Server server) {
        this.clientName = clientName;
        setCurrentMenu(AccountMenu.getInstance());
        this.server = server;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void updateLeaderBoard(String serverName)  {
        this.addToSendingMessages(Message.makeGetLeaderBoardMessage(clientName, serverName, 0));
        this.sendMessages();
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(AccountInfo[] leaderBoard) {
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
        sendingMessages.add(message);
    }

    public void addToReceivingMessages(String messageJson) {
        receivingMessages.add(Message.convertJsonToMessage(messageJson));
    }

    private void receiveMessages()  {
        validation = true;
        for (Message message : receivingMessages) {
            switch (message.getMessageType()) {
                case SEND_EXCEPTION:
                    validation = false;
                    errorMessage = message.getExceptionString();
                case ACCOUNT_COPY:
                    account = new Account(message.getAccount());
                case GAME_COPY:
                    game = message.getGame();
                    break;
                case ORIGINAL_CARDS_COPY:
                    originalCards = message.getShopCards();
                    break;
                case CUSTOM_DECKS_COPY:
                    customDecks = message.getCustomDecks();
                    break;
                case LEADERBOARD_COPY:
                    leaderBoard = message.getLeaderBoard();
                    break;
                case STORIES_COPY:

                    break;
                case POSITIONS_COPY:

                    break;
            }
        }
        receivingMessages.clear();
    }

    public void sendMessages()  {
        for (Message message : sendingMessages) {
            server.addToReceivingMessages(message.toJson());
        }
        sendingMessages.clear();
        server.receiveMessages();
        receiveMessages();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setCustomDecks(DeckInfo[] customDecks) {
        this.customDecks = customDecks;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }
}