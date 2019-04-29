package server;

import client.Client;
import server.models.JsonConverter;
import server.models.account.Account;
import server.models.account.AccountInfo;
import server.models.account.Collection;
import server.models.account.TempAccount;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.Deck;
import server.models.game.*;
import server.models.map.GameMap;
import server.models.message.Message;
import server.models.sorter.LeaderBoardSorter;

import java.io.*;
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
    private Collection originalCards = new Collection(); // TODO: collectibles may be in a different field
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
            if (message == null) {
                serverPrint("invalid message");
                continue;
            }
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
                case NEW_MULTIPLAYER_GAME:
                    newMultiplayerGame(message);
                    break;
                case NEW_STORY_GAME:

                    break;
                case NEW_DECK_GAME:

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
                case USE_SPECIAL_POWER:
                    useSpell(message);
                    break;
                case MOVE_TROOP:
                    moveTroop(message);
                    break;
                case SUDO:
                    sudo(message);
                    break;
                case SELECT_USER:
                    selectUserForMultiPlayer(message);
                    break;
                case SAVE_CHANGES:
                    serverPrint("Auto Save is On!");
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

    private Account getAccount(String username) {
        if (username == null) {
            serverPrint("Null Username In getAccount.");
            return null;
        }
        for (Map.Entry<Account, String> map : accounts.entrySet()) {
            if (map.getKey().getUsername().equalsIgnoreCase(username)) {
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
            if (client.getClientName().equalsIgnoreCase(clientName))
                return client;
        }
        return null;
    }

    public String getClientName(String username) {
        Account account = getAccount(username);
        if (account == null)
            return null;
        return accounts.get(account);
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

    private boolean loginCheck(Message message) {
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
        if (loginCheck(message)) {
            accounts.replace(clients.get(message.getSender()), null);
            clients.replace(message.getSender(), null);
            serverPrint(message.getSender() + " Is Logged Out.");
        }
    }

    private void createDeck(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                account.addDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            } else {
                addToSendingMessages(Message.makeExceptionMessage(
                        serverName, message.getSender(), "deck's name was duplicate.", message.getMessageId()));
            }
        }
    }

    private void removeDeck(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (account.hasDeck(message.getDeckName())) {
                account.deleteDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            } else {
                addToSendingMessages(Message.makeExceptionMessage(
                        serverName, message.getSender(), "deck was not found.", message.getMessageId()));
            }
        }
    }

    private void addToDeck(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found.", message.getSender(), message.getMessageId());
            } else if (!account.getCollection().hasCard(message.getCardId())) {
                sendException("invalid cardid.", message.getSender(), message.getMessageId());
            } else if (account.getDeck(message.getDeckName()).hasCard(message.getCardId())) {
                sendException("deck had this card.", message.getSender(), message.getMessageId());
            } else {
                account.addCardToDeck(message.getCardId(), message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            }
        }
    }

    private void removeFromDeck(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found.", message.getSender(), message.getMessageId());
            } else if (!account.getDeck(message.getDeckName()).hasCard(message.getCardId())) {
                sendException("deck didn't have this card.", message.getSender(), message.getMessageId());
            } else {
                account.removeCardFromDeck(message.getCardId(), message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            }
        }
    }

    private void selectDeck(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!account.hasDeck(message.getDeckName())) {
                sendException("deck was not found", message.getSender(), message.getMessageId());

            } else if (account.getMainDeck() != null && account.getMainDeck().getDeckName().equals(message.getDeckName())) {
                sendException("deck was already the main deck.", message.getSender(), message.getMessageId());

            } else {
                account.selectDeck(message.getDeckName());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            }
        }
    }

    private void buyCard(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!originalCards.hasCard(message.getCardName()) || originalCards.getCard(message.getCardName()).getType() == CardType.COLLECTIBLE_ITEM) { // TODO
                sendException("invalid card name", message.getSender(), message.getMessageId());
            } else if (account.getMoney() < originalCards.getCard(message.getCardName()).getPrice()) {
                sendException("account's money isn't enough.", message.getSender(), message.getMessageId());
            } else {
                account.buyCard(message.getCardName(), originalCards.getCard(message.getCardName()).getPrice(), originalCards);
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            }
        }
    }

    private void sellCard(Message message) {
        if (loginCheck(message)) {
            Account account = clients.get(message.getSender());
            if (!account.getCollection().hasCard(message.getCardId())) {
                sendException("invalid card id", message.getSender(), message.getMessageId());
            } else {
                account.sellCard(message.getCardId());
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, message.getSender(), account, message.getMessageId()));
                saveAccount(account);
            }
        }
    }

    private void sendStories(Message message) {
        if (loginCheck(message)) {
            addToSendingMessages(Message.makeStoriesCopyMessage
                    (serverName, message.getSender(), stories.toArray(Story[]::new), message.getMessageId()));
        }
    }

    private void sendOriginalCards(Message message) {
        if (loginCheck(message)) {
            addToSendingMessages(Message.makeOriginalCardsCopyMessage(
                    serverName, message.getSender(), originalCards, message.getMessageId()));
        }
    }

    private void sendLeaderBoard(Message message) {//Check
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

    private void selectUserForMultiPlayer(Message message) {
        Account account = getAccount(message.getOpponentUserName());
        if (account == null) {
            sendException("second player is not valid", message.getSender(), 0);
        } else if (!account.hasValidMainDeck()) {
            sendException("selected deck for second player is not valid", message.getSender(), 0);
        } else {
            AccountInfo accountInfo = new AccountInfo(account);
            addToSendingMessages(
                    Message.makeAccountInfoMessage(
                            serverName, message.getSender(), accountInfo, 0
                    )
            );
            sendMessages();
        }
    }

    private Game getGame(String clientName) throws Exception {
        Account account = clients.get(clientName);
        if (account == null) {
            throw new Exception("your client hasn't logged in!");
        }
        Game game = onlineGames.get(account);
        if (game == null) {
            throw new Exception("you don't have online game!");
        }
        return game;
    }

    private boolean isOpponentAccountValid(Message message) {
        if (message.getOpponentUserName() == null) {
            sendException("invalid opponentAccount!", message.getSender(), message.getMessageId());
            return false;
        }
        Account opponentAccount = getAccount(message.getOpponentUserName());
        if (opponentAccount == null) {
            sendException("invalid opponentAccount!", message.getSender(), message.getMessageId());
            return false;
        }
        /*if (accounts.get(opponentAccount) == null) {
            sendException("opponentAccount has not logged in!", message.getSender(), message.getMessageId());
            return false;
        }*/
        return true;
    }

    private void newMultiplayerGame(Message message) {
        if (loginCheck(message) && isOpponentAccountValid(message)) {
            Account myAccount = clients.get(message.getSender());
            Account opponentAccount = getAccount(message.getOpponentUserName());
            if (!myAccount.hasValidMainDeck()) {
                sendException("you don't have valid main deck!", message.getSender(), message.getMessageId());
                return;
            }
            if (!opponentAccount.hasValidMainDeck()) {
                sendException("opponent doesn't have valid main deck!", message.getSender(), message.getMessageId());
                return;
            }
            if (onlineGames.get(myAccount) != null) {
                sendException("you have online game!", message.getSender(), message.getMessageId());
                return;
            }
            if (onlineGames.get(opponentAccount) != null) {
                sendException("opponent has online game!", message.getSender(), message.getMessageId());
                return;
            }
            //Should be removed
            accounts.replace(opponentAccount, onlineClients.get(1).getClientName());
            clients.replace(onlineClients.get(1).getClientName(), opponentAccount);
            Game game = null;
            GameMap gameMap = new GameMap(new HashMap<>(), message.getNumberOfFlags());
            if (message.getGameType() == null) {
                sendException("invalid gameType!", message.getSender(), message.getMessageId());
                return;
            }
            switch (message.getGameType()) {
                case KILL_HERO:
                    game = new KillHeroBattle(message.getGameType(), myAccount, opponentAccount, gameMap);
                    break;
                case A_FLAG:
                    game = new SingleFlagBattle(message.getGameType(), myAccount, opponentAccount, gameMap);
                    break;
                case SOME_FLAG:
                    game = new MultiFlagBattle(message.getGameType(), myAccount, opponentAccount, gameMap);
                    break;
            }
            onlineGames.replace(myAccount, game);
            onlineGames.replace(opponentAccount, game);
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, message.getSender(), game, message.getMessageId()));
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, accounts.get(opponentAccount), game, 0));
        }
    }

    private void insertCard(Message message) {
        Game game;
        try {
            game = getGame(message.getSender());
            try {
                game.insert(clients.get(message.getSender()).getUsername(), message.getCardId(), message.getPosition());
            } catch (Exception e) {
                sendException(e.getMessage(), message.getSender(), message.getMessageId());
            }
        } catch (Exception e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void attack(Message message) {
        Game game;
        try {
            game = getGame(message.getSender());
            try {
                game.attack(clients.get(message.getSender()).getUsername(), message.getCardIds()[0], message.getCardIds()[1]);
            } catch (Exception e) {
                sendException(e.getMessage(), message.getSender(), message.getMessageId());
            }
        } catch (Exception e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void combo(Message message) {
        Game game;
        try {
            game = getGame(message.getSender());
            try {
                game.comboAttack(clients.get(message.getSender()).getUsername(), message.getCardIds(), message.getCardId());
            } catch (Exception e) {
                sendException(e.getMessage(), message.getSender(), message.getMessageId());
            }
        } catch (Exception e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void useSpell(Message message) {

    }

    private void moveTroop(Message message) {
        Game game;
        try {
            game = getGame(message.getSender());
            try {
                game.moveTroop(clients.get(message.getSender()).getUsername(), message.getCardId(), message.getPosition());
            } catch (Exception e) {
                sendException(e.getMessage(), message.getSender(), message.getMessageId());
            }
        } catch (Exception e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void endTurn(Message message) {
        Game game;
        try {
            game = getGame(message.getSender());
            try {
                game.changeTurn(clients.get(message.getSender()).getUsername());
            } catch (Exception e) {
                sendException(e.getMessage(), message.getSender(), message.getMessageId());
            }
        } catch (Exception e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void sudo(Message message) {
        String command = message.getSudoCommand().toLowerCase();
        if (command.contains("account")) {
            for (Map.Entry<Account, String> account : accounts.entrySet()) {
                serverPrint(account.getKey().getUsername() + " " + account.getKey().getPassword());
            }
        }

    }

    private void checkGameAccountsClient(String client1, String client2) throws Exception {
        if (client1 == null) {
            throw new Exception("Player1 has logged out!");
        }
        if (client2 == null) {
            throw new Exception("Player2 has logged out!");
        }
    }

    public void sendAddToHandMessage(Game game, String cardId) throws Exception {
        String client1 = getClientName(game.getPlayerOne().getUserName());
        String client2 = getClientName(game.getPlayerTwo().getUserName());
        checkGameAccountsClient(client1, client2);
        addToSendingMessages(Message.makeToHandMessage
                (serverName, client1, cardId, 0));
        addToSendingMessages(Message.makeToHandMessage
                (serverName, client2, cardId, 0));
    }

    public void sendAddToNextMessage(Game game, String cardId) throws Exception {
        String client1 = getClientName(game.getPlayerOne().getUserName());
        String client2 = getClientName(game.getPlayerTwo().getUserName());
        checkGameAccountsClient(client1, client2);
        addToSendingMessages(Message.makeToNextMessage
                (serverName, client1, cardId, 0));
        addToSendingMessages(Message.makeToNextMessage
                (serverName, client2, cardId, 0));
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
            return JsonConverter.fromJson(new BufferedReader(new FileReader(file)), classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveAccount(Account account) {
        String accountJson = JsonConverter.toJson(new TempAccount(account));

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