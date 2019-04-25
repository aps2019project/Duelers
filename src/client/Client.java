package client;

import client.models.account.Account;
import client.models.card.Card;
import client.models.card.Deck;
import client.models.game.Game;
import client.models.map.Position;
import client.models.menus.AccountMenu;
import client.models.menus.Menu;
import client.models.message.Message;
import client.view.request.InputException;
import server.Server;

import java.util.ArrayList;

public class Client {
    private Server server;
    private String clientName;
    private Account account;
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();
    private Game game;
    private Deck[] customDecks;
    private ArrayList<Account> leaderBoard = new ArrayList<>();
    private Menu currentMenu;
    private Card selected;
    private ArrayList<Position> positions = new ArrayList<>();
    private boolean validation = true;
    private String errorMessage;

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
                    account = message.getAccount();
                case GAME_COPY:
                    game = message.getGame();
                    break;
                case ORIGINAL_CARDS_COPY:

                    break;
                case CUSTOM_DECKS_COPY:
                    customDecks = message.getCustomDecks();
                    break;
                case LEADERBOARD_COPY:
//                    leaderBoard = message.getLeaderBoard();
                    break;
                case STORIES_COPY:
                    break;
                case POSITIONS_COPY:
                    break;
                case TO_MAP:
                    break;
                case TO_HAND:
                    break;
                case TO_NEXT:
                    break;
                case TO_COLLECTED_CARDS:
                    break;
                case TO_GRAVEYARD:
                    break;
                case MOVE_TROOP:
                    break;
                case TROOP_AP:
                    break;
                case TROOP_HP:
                    break;
                case GET_ORIGINAL_CARDS:
                    break;
                case GET_LEADERBOARD:
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
                case BUY_CARD:
                    break;
                case SELL_CARD:
                    break;
                case INSERT:
                    break;
                case ATTACK:
                    break;
                case COMBO:
                    break;
                case USE_SPELL:
                    break;
                case END_TURN:
                    break;
                case LOG_IN:
                    break;
                case LOG_OUT:
                    break;
                case REGISTER:
                    break;
                case NEW_GAME:
                    break;
                case SELECT_USER:
                    break;
                case SHOP_SEARCH:
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

    public void setCustomDecks(Deck[] customDecks) {
        this.customDecks = customDecks;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }
}