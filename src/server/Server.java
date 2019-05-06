package server;

import client.Client;
import server.models.JsonConverter;
import server.models.account.Account;
import server.models.account.Collection;
import server.models.account.MatchHistory;
import server.models.account.TempAccount;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.Deck;
import server.models.exceptions.ClientException;
import server.models.exceptions.LogicException;
import server.models.exceptions.ServerException;
import server.models.game.*;
import server.models.map.GameMap;
import server.models.message.CardPosition;
import server.models.message.Message;
import server.models.sorter.LeaderBoardSorter;
import server.models.sorter.StoriesSorter;

import java.io.*;
import java.util.*;

public class Server {
    private static final String ACCOUNTS_PATH = "jsonData/accounts";
    private static final String[] CARDS_PATHS = {
            "jsonData/heroCards",
            "jsonData/minionCards",
            "jsonData/spellCards",
            "jsonData/itemCards/collectible",
            "jsonData/itemCards/usable"};
    private static final String FLAG_PATH = "jsonData/itemCards/flag/Flag.item.card.json";
    private static final String STORIES_PATH = "jsonData/stories";

    private static Server server;
    private String serverName;

    private HashMap<Account, String> accounts = new HashMap<>();//Account -> ClientName
    private HashMap<String, Account> clients = new HashMap<>();//clientName -> Account
    private HashMap<Account, Game> onlineGames = new HashMap<>();//Account -> Game
    private ArrayList<Client> onlineClients = new ArrayList<>();

    private Collection originalCards = new Collection();
    private ArrayList<Card> collectibleItems = new ArrayList<>();
    private Card originalFlag;
    private ArrayList<Story> stories = new ArrayList<>();

    private LinkedList<Message> sendingMessages = new LinkedList<>();//TODO:queue
    private LinkedList<Message> receivingMessages = new LinkedList<>();

    private Server(String serverName) {
        this.serverName = serverName;
        serverPrint("Server Was Created!");
    }

    public static Server getInstance() {
        if (server == null)
            server = new Server("Server");
        return server;
    }

    public void start() {
        readAccounts();
        readAllCards();
        readStories();
        new Thread(() -> {
            serverPrint("Server Thread:sending messages is started...");
            while (true) {
                synchronized (sendingMessages) {
                    while (!sendingMessages.isEmpty()) {
                        sendMessage(sendingMessages.poll());
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (Exception e) {

                }
            }
        }).start();
        new Thread(() -> {
            serverPrint("Server Thread:receiving messages is started...");
            while (true) {
                synchronized (receivingMessages) {
                    while (!receivingMessages.isEmpty()) {
                        receiveMessage(receivingMessages.poll());
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (Exception e) {

                }
            }
        }).start();
    }

    public void addClient(Client client) {
        if (client == null || client.getClientName().length() < 2) {
            serverPrint("Invalid Client(invalid name) Was Not Added.");
        } else if (clients.containsKey(client.getClientName())) {
            serverPrint("Client Name Was Duplicate.");
        } else {
            onlineClients.add(client);
            clients.put(client.getClientName(), null);
            serverPrint("Client:" + client.getClientName() + " Was Added!");
        }
    }

    private void addToSendingMessages(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
        }
    }

    public void addToReceivingMessages(String messageJson) {
        synchronized (receivingMessages) {
            Message temp = Message.convertJsonToMessage(messageJson);
            receivingMessages.add(temp);
        }
    }

    private void receiveMessage(Message message) {//TODO:add finish game message
        try {
            if (message == null) {
                throw new ServerException("NULL Message");
            }
            if (!message.getReceiver().equals(serverName)) {
                throw new ServerException("Message's Receiver Was Not This Server.");
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
                case GET_DATA:
                    switch (message.getGetDataMessage().getDataName()) {
                        case LEADERBOARD:
                            sendLeaderBoard(message);
                            break;
                        case ORIGINAL_CARDS:
                            sendOriginalCards(message);
                            break;
                        case STORIES:
                            sendStories(message);
                            break;
                    }
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
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case NEW_STORY_GAME:
                    newStoryGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case NEW_DECK_GAME:
                    newDeckGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case INSERT:
                    insertCard(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case ATTACK:
                    attack(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case END_TURN:
                    endTurn(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case COMBO:
                    combo(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case USE_SPECIAL_POWER:
                    useSpecialPower(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case MOVE_TROOP:
                    moveTroop(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case SELECT_USER:
                    selectUserForMultiPlayer(message);
                    break;
                case SUDO:
                    sudo(message);
                    break;
                default:
                    throw new LogicException("Invalid Message Type!");
            }
        } catch (ServerException e) {
            serverPrint(e.getMessage());
            if (message != null) {
                sendException("server has error:(", message.getSender(), message.getMessageId());
            }
        } catch (ClientException e) {
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        } catch (LogicException e) {
            serverPrint(e.getMessage());
            sendException(e.getMessage(), message.getSender(), message.getMessageId());
        }
    }

    private void sendMessage(Message message) {
        Client client = getClient(message.getReceiver());
        if (client == null) {
            serverPrint("Message's Client Was Not Found.");
        } else {
            client.addToReceivingMessages(message.toJson());
        }
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
            if (client.getClientName().equals(clientName))
                return client;
        }
        return null;
    }

    private String getClientName(String username) {
        Account account = getAccount(username);
        if (account == null)
            return null;
        return accounts.get(account);
    }

    private void sendException(String exceptionString, String receiver, int messageId) {
        addToSendingMessages(Message.makeExceptionMessage(
                serverName, receiver, exceptionString, messageId));
    }

    private void register(Message message) throws LogicException {
        if (message.getAccountFields().getUsername() == null || message.getAccountFields().getUsername().length() < 2
                || getAccount(message.getAccountFields().getUsername()) != null) {
            throw new ClientException("Invalid Username!");
        } else if (message.getAccountFields().getPassword() == null || message.getAccountFields().getPassword().length() < 4) {
            throw new ClientException("Invalid Password!");
        } else {
            Account account = new Account(message.getAccountFields().getUsername(), message.getAccountFields().getPassword());
            accounts.put(account, null);
            onlineGames.put(account, null);
            saveAccount(account);
            serverPrint(message.getAccountFields().getUsername() + " Is Created!");
            login(message);
        }
    }

    private void login(Message message) throws LogicException {
        if (message.getAccountFields().getUsername() == null || message.getSender() == null) {
            throw new ClientException("invalid message!");
        }
        Account account = getAccount(message.getAccountFields().getUsername());
        Client client = getClient(message.getSender());
        if (client == null) {
            throw new LogicException("Client Wasn't Added!");
        } else if (account == null) {
            throw new ClientException("Username Not Found!");
        } else if (!account.getPassword().equals(message.getAccountFields().getPassword())) {
            throw new ClientException("Incorrect PassWord!");
        } else if (accounts.get(account) != null) {
            throw new ClientException("Selected Username Is Online!");
        } else if (clients.get(message.getSender()) != null) {
            throw new ClientException("Your Client Has Logged In Before!");
        } else {
            accounts.replace(account, message.getSender());
            clients.replace(message.getSender(), account);
            addToSendingMessages(Message.makeAccountCopyMessage(
                    serverName, message.getSender(), account, message.getMessageId()));
            serverPrint(message.getSender() + " Is Logged In");
        }
    }

    private void loginCheck(Message message) throws LogicException {
        if (message.getSender() == null) {
            throw new ClientException("invalid message!");
        }
        if (!clients.containsKey(message.getSender())) {
            throw new LogicException("Client Wasn't Added!");
        }
        if (clients.get(message.getSender()) == null) {
            throw new ClientException("Client Was Not LoggedIn");
        }
    }

    private void logout(Message message) throws LogicException {
        loginCheck(message);
        accounts.replace(clients.get(message.getSender()), null);
        clients.replace(message.getSender(), null);
        serverPrint(message.getSender() + " Is Logged Out.");
        //TODO:Check online games
        addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
    }

    private void createDeck(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.addDeck(message.getOtherFields().getDeckName());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void removeDeck(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.deleteDeck(message.getOtherFields().getDeckName());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void addToDeck(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.addCardToDeck(message.getOtherFields().getMyCardId(), message.getOtherFields().getDeckName());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void removeFromDeck(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.removeCardFromDeck(message.getOtherFields().getMyCardId(), message.getOtherFields().getDeckName());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void selectDeck(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.selectDeck(message.getOtherFields().getDeckName());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void buyCard(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.buyCard(message.getOtherFields().getCardName(), originalCards.getCard(message.getOtherFields().getCardName()).getPrice(), originalCards);
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void sellCard(Message message) throws LogicException {
        loginCheck(message);
        Account account = clients.get(message.getSender());
        account.sellCard(message.getOtherFields().getMyCardId());
        addToSendingMessages(Message.makeAccountCopyMessage(
                serverName, message.getSender(), account, message.getMessageId()));
        saveAccount(account);
    }

    private void sendStories(Message message) throws LogicException {
        loginCheck(message);
        addToSendingMessages(Message.makeStoriesCopyMessage
                (serverName, message.getSender(), stories.toArray(Story[]::new), message.getMessageId()));
    }

    private void sendOriginalCards(Message message) throws LogicException {
        loginCheck(message);
        addToSendingMessages(Message.makeOriginalCardsCopyMessage(
                serverName, message.getSender(), originalCards, message.getMessageId()));

    }

    private void sendLeaderBoard(Message message) throws ClientException { //Check
        if (accounts.size() == 0) {
            throw new ClientException("leader board is empty");
        }
        Account[] leaderBoard = new Account[accounts.size()];
        int counter = 0;
        for (Account account : accounts.keySet()) {
            leaderBoard[counter] = account;
            counter++;
        }
        Arrays.sort(leaderBoard, new LeaderBoardSorter());
        addToSendingMessages(Message.makeLeaderBoardCopyMessage(serverName, message.getSender(), leaderBoard, message.getMessageId()));
    }

    private void selectUserForMultiPlayer(Message message) throws ClientException {
        Account account = getAccount(message.getNewGameFields().getOpponentUsername());
        if (account == null) {
            throw new ClientException("second player is not valid");
        } else if (!account.hasValidMainDeck()) {
            throw new ClientException("selected deck for second player is not valid");
        } else {
            addToSendingMessages(Message.makeAccountInfoMessage(serverName, message.getSender(), account, message.getMessageId()));
        }
    }

    private Game getGame(String clientName) throws ClientException {
        Account account = clients.get(clientName);
        if (account == null) {
            throw new ClientException("your client hasn't logged in!");
        }
        Game game = onlineGames.get(account);
        if (game == null) {
            throw new ClientException("you don't have online game!");
        }
        return game;
    }

    private boolean isOpponentAccountValid(Message message) {
        if (message.getNewGameFields().getOpponentUsername() == null) {
            sendException("invalid opponentAccount!", message.getSender(), message.getMessageId());
            return false;
        }
        Account opponentAccount = getAccount(message.getNewGameFields().getOpponentUsername());
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

    private void newDeckGame(Message message) throws LogicException {
        loginCheck(message);
            Account myAccount = clients.get(message.getSender());
            if (!myAccount.hasValidMainDeck()) {
                throw new ClientException("you don't have valid main deck!");
            }
            if (onlineGames.get(myAccount) != null) {
                throw new ClientException("you have online game!");
            }
            Deck deck = myAccount.getDeck(message.getOtherFields().getDeckName());
            if (deck == null || !deck.isValid()) {
                throw new ClientException("selected deck is not valid");
            }
            Game game = null;
            GameMap gameMap = new GameMap(collectibleItems, message.getNewGameFields().getNumberOfFlags(), originalFlag);
            switch (message.getNewGameFields().getGameType()) {
                case KILL_HERO:
                    game = new KillHeroBattle(myAccount, deck, gameMap);
                    break;
                case A_FLAG:
                    game = new SingleFlagBattle(myAccount, deck, gameMap);
                    break;
                case SOME_FLAG:
                    game = new MultiFlagBattle(myAccount, deck, gameMap, message.getNewGameFields().getNumberOfFlags());
                    break;
            }
            onlineGames.put(myAccount, game);
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, message.getSender(), game, 0));
            game.startGame();
    }

    private void newStoryGame(Message message) throws LogicException {
        loginCheck(message);
            Account myAccount = clients.get(message.getSender());
            if (!myAccount.hasValidMainDeck()) {
                throw new ClientException("you don't have valid main deck!");
            }
            if (onlineGames.get(myAccount) != null) {
                throw new ClientException("you have online game!");
            }
            Game game = null;
            Story story = stories.get(message.getNewGameFields().getStage());
            GameMap gameMap = new GameMap(collectibleItems, story.getNumberOfFlags(), originalFlag);
            switch (story.getGameType()) {
                case KILL_HERO:
                    game = new KillHeroBattle(myAccount, story.getDeck(), gameMap);
                    break;
                case A_FLAG:
                    game = new SingleFlagBattle(myAccount, story.getDeck(), gameMap);
                    break;
                case SOME_FLAG:
                    game = new MultiFlagBattle(myAccount, story.getDeck(), gameMap, story.getNumberOfFlags());
                    break;
            }
            onlineGames.put(myAccount, game);
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, message.getSender(), game, 0));
            game.startGame();

    }

    private void newMultiplayerGame(Message message) throws LogicException {
        loginCheck(message);
        if (isOpponentAccountValid(message)) {
            Account myAccount = clients.get(message.getSender());
            Account opponentAccount = getAccount(message.getNewGameFields().getOpponentUsername());
            if (!myAccount.hasValidMainDeck()) {
                throw new ClientException("you don't have valid main deck!");
            }
            if (opponentAccount == null || !opponentAccount.hasValidMainDeck()) {
                throw new ClientException("opponent's main deck is not valid");
            }
            if (onlineGames.get(myAccount) != null) {
                throw new ClientException("you have online game!");
            }
            if (onlineGames.get(opponentAccount) != null) {
                throw new ClientException("opponent has online game!");
            }
            //Should be removed
            accounts.replace(opponentAccount, onlineClients.get(1).getClientName());
            clients.replace(onlineClients.get(1).getClientName(), opponentAccount);
            Game game = null;
            GameMap gameMap = new GameMap(collectibleItems, message.getNewGameFields().getNumberOfFlags(), originalFlag);
            if (message.getNewGameFields().getGameType() == null) {
                throw new ClientException("invalid gameType!");
            }
            switch (message.getNewGameFields().getGameType()) {
                case KILL_HERO:
                    game = new KillHeroBattle(myAccount, opponentAccount, gameMap);
                    break;
                case A_FLAG:
                    game = new SingleFlagBattle(myAccount, opponentAccount, gameMap);
                    break;
                case SOME_FLAG:
                    game = new MultiFlagBattle(myAccount, opponentAccount, gameMap, message.getNewGameFields().getNumberOfFlags());
                    break;
            }
            onlineGames.put(myAccount, game);
            onlineGames.put(opponentAccount, game);
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, message.getSender(), game, 0));
            addToSendingMessages(Message.makeGameCopyMessage
                    (serverName, accounts.get(opponentAccount), game, 0));
            game.startGame();
        }
    }


    private void insertCard(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.insert(clients.get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());
        checkGameFinish(game);
    }

    private void attack(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.attack(clients.get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getOpponentCardId());
        checkGameFinish(game);
    }

    private void combo(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.comboAttack(clients.get(message.getSender()).getUsername(), message.getOtherFields().getMyCardIds(), message.getOtherFields().getOpponentCardId());
        checkGameFinish(game);
    }

    private void useSpecialPower(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.useSpecialPower(clients.get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());
        checkGameFinish(game);
    }

    private void moveTroop(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.moveTroop(clients.get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());
        checkGameFinish(game);
    }

    private void endTurn(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        game.changeTurn(clients.get(message.getSender()).getUsername());
        checkGameFinish(game);
    }

    private void sudo(Message message) {
        String command = message.getOtherFields().getSudoCommand().toLowerCase();
        if (command.contains("account")) {
            for (Map.Entry<Account, String> account : accounts.entrySet()) {
                serverPrint(account.getKey().getUsername() + " " + account.getKey().getPassword());
            }
        }
        addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
    }

    private void checkGameFinish(Game game) {
        if (game.finishCheck()) {
            MatchHistory playerOneHistory = game.getPlayerOne().getMatchHistory();
            MatchHistory playerTwoHistory = game.getPlayerTwo().getMatchHistory();
            if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
                Account account = getAccount(game.getPlayerOne().getUserName());
                if (account == null)
                    serverPrint("Error");
                else {
                    account.addMatchHistory(playerOneHistory);
                    saveAccount(account);
                }
            }
            if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
                Account account = getAccount(game.getPlayerTwo().getUserName());
                if (account == null)
                    serverPrint("Error");
                else {
                    account.addMatchHistory(playerTwoHistory);
                    saveAccount(account);
                }
            }
            sendGameFinishMessages(game);
        }
    }

    public void sendChangeCardPositionMessage(Game game, Card card, CardPosition newCardPosition) throws ServerException {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                throw new ServerException("player one has logged out during game!");
            }
            addToSendingMessages(Message.makeChangeCardPositionMessage(
                    serverName, clientName, card, newCardPosition, 0));
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                throw new ServerException("player two has logged out during game!");
            }
            addToSendingMessages(Message.makeChangeCardPositionMessage(
                    serverName, clientName, card, newCardPosition, 0));
        }
    }

    public void sendTroopUpdateMessage(Game game, Troop troop) throws ServerException {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                throw new ServerException("player one has logged out during game!");
            }
            addToSendingMessages(Message.makeTroopUpdateMessage(
                    serverName, clientName, troop, 0));
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                throw new ServerException("player two has logged out during game!");
            }
            addToSendingMessages(Message.makeTroopUpdateMessage(
                    serverName, clientName, troop, 0));
        }
    }

    public void sendGameUpdateMessage(Game game) throws ServerException {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                throw new ServerException("player one has logged out during game!");
            }
            addToSendingMessages(Message.makeGameUpdateMessage(
                    serverName, clientName, game.getTurnNumber(), game.getPlayerOne().getCurrentMP(),
                    game.getPlayerOne().getNumberOfCollectedFlags(), game.getPlayerTwo().getCurrentMP(),
                    game.getPlayerTwo().getNumberOfCollectedFlags(), 0));
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                throw new ServerException("player two has logged out during game!");
            }
            addToSendingMessages(Message.makeGameUpdateMessage(
                    serverName, clientName, game.getTurnNumber(), game.getPlayerOne().getCurrentMP(),
                    game.getPlayerOne().getNumberOfCollectedFlags(), game.getPlayerTwo().getCurrentMP(),
                    game.getPlayerTwo().getNumberOfCollectedFlags(), 0));
        }
    }

    private void sendGameFinishMessages(Game game) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameFinishMessage(
                        serverName, clientName, game.getPlayerOne().getMatchHistory().isAmIWinner(), 0));
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, clientName, getAccount(game.getPlayerOne().getUserName()), 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameFinishMessage(
                        serverName, clientName, game.getPlayerTwo().getMatchHistory().isAmIWinner(), 0));
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, clientName, getAccount(game.getPlayerTwo().getUserName()), 0));
            }
        }
    }

    private void readAccounts() {
        File[] files = new File(ACCOUNTS_PATH).listFiles();
        if (files != null) {
            for (File file : files) {
                TempAccount account = loadFile(file, TempAccount.class);
                if (account == null) continue;
                Account newAccount = new Account(account);
                accounts.put(newAccount, null);
                onlineGames.put(newAccount, null);
            }
        }
        serverPrint("Accounts loaded");
    }

    private void readAllCards() {
        for (String path : CARDS_PATHS) {
            File[] files = new File(path).listFiles();
            if (files != null) {
                for (File file : files) {
                    Card card = loadFile(file, Card.class);
                    if (card == null) continue;

                    if (card.getType() == CardType.COLLECTIBLE_ITEM) {
                        collectibleItems.add(card);
                    } else {
                        originalCards.addCard(card);
                    }
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
        stories.sort(new StoriesSorter());
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