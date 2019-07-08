package server;

import server.chatCenter.ChatCenter;
import server.clientPortal.ClientPortal;
import server.clientPortal.models.message.CardPosition;
import server.clientPortal.models.message.Message;
import server.dataCenter.DataCenter;
import server.dataCenter.models.account.Account;
import server.dataCenter.models.account.AccountType;
import server.dataCenter.models.card.Card;
import server.exceptions.ClientException;
import server.exceptions.LogicException;
import server.exceptions.ServerException;
import server.gameCenter.GameCenter;
import server.gameCenter.models.game.CellEffect;
import server.gameCenter.models.game.Game;
import server.gameCenter.models.game.Story;
import server.gameCenter.models.game.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    private static Server server;
    public final String serverName;

    private final Queue<Message> sendingMessages = new LinkedList<>();
    private final Queue<Message> receivingMessages = new LinkedList<>();

    private Server(String serverName) {
        this.serverName = serverName;
        serverPrint("Server Was Created!");
    }

    public static Server getInstance() {
        return server;
    }

    public static void main(String[] args) {
        server = new Server("Server");
        server.start();
    }

    private void start() {
        DataCenter.getInstance().run();//no thread
        GameCenter.getInstance().start();
        ClientPortal.getInstance().start();

        new Thread(() -> {
            serverPrint("Server Thread:sending messages is started...");
            while (true) {
                Message message;
                synchronized (sendingMessages) {
                    message = sendingMessages.poll();
                }
                if (message != null) {
                    ClientPortal.getInstance().sendMessage(message.getReceiver(), message.toJson());
                    System.out.println(message.getReceiver() + ":\n" + message.toJson());//TODO:remove
                } else {
                    try {
                        synchronized (sendingMessages) {
                            sendingMessages.wait();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
        new Thread(() -> {
            serverPrint("Server Thread:receiving messages is started...");
            while (true) {
                Message message;
                synchronized (receivingMessages) {
                    message = receivingMessages.poll();
                }
                if (message != null) {
                    receiveMessage(message);
                } else {
                    try {
                        synchronized (receivingMessages) {
                            receivingMessages.wait();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    public void addToSendingMessages(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
            sendingMessages.notify();
        }
    }

    public void addToReceivingMessages(Message message) {
        synchronized (receivingMessages) {
            receivingMessages.add(message);
            receivingMessages.notify();
        }
    }

    private void receiveMessage(Message message) {//TODO:add fast forceFinish game message
        try {
            if (message == null) {
                throw new ServerException("NULL Message");
            }
            if (!message.getReceiver().equals(serverName)) {
                throw new ServerException("Message's Receiver Was Not This Server.");
            }
            switch (message.getMessageType()) {
                case REGISTER:
                    DataCenter.getInstance().register(message);
                    break;
                case LOG_IN:
                    DataCenter.getInstance().login(message);
                    break;
                case LOG_OUT:
                    DataCenter.getInstance().logout(message);
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
                        case CUSTOM_CARDS:
                            sendCustomCards(message);
                            break;
                    }
                    break;
                case BUY_CARD:
                    DataCenter.getInstance().buyCard(message);
                    break;
                case SELL_CARD:
                    DataCenter.getInstance().sellCard(message);
                    break;
                case CREATE_DECK:
                    DataCenter.getInstance().createDeck(message);
                    break;
                case REMOVE_DECK:
                    DataCenter.getInstance().removeDeck(message);
                    break;
                case IMPORT_DECK:
                    DataCenter.getInstance().importDeck(message);
                    break;
                case ADD_TO_DECK:
                    DataCenter.getInstance().addToDeck(message);
                    break;
                case REMOVE_FROM_DECK:
                    DataCenter.getInstance().removeFromDeck(message);
                    break;
                case SELECT_DECK:
                    DataCenter.getInstance().selectDeck(message);
                    break;
                case MULTIPLAYER_GAME_REQUEST:
                    GameCenter.getInstance().getMultiPlayerGameRequest(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case CANCEL_REQUEST:
                    GameCenter.getInstance().getCancelRequest(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case ACCEPT_REQUEST:
                    GameCenter.getInstance().getAcceptRequest(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case DECLINE_REQUEST:
                    GameCenter.getInstance().getDeclineRequest(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case NEW_STORY_GAME:
                    GameCenter.getInstance().newStoryGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));//TODO:can be removed
                    break;
                case NEW_DECK_GAME:
                    GameCenter.getInstance().newDeckGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case INSERT:
                    GameCenter.getInstance().insertCard(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case ATTACK:
                    GameCenter.getInstance().attack(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case END_TURN:
                    GameCenter.getInstance().endTurn(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case COMBO:
                    GameCenter.getInstance().combo(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case USE_SPECIAL_POWER:
                    GameCenter.getInstance().useSpecialPower(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case MOVE_TROOP:
                    GameCenter.getInstance().moveTroop(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case FORCE_FINISH:
                    GameCenter.getInstance().forceFinishGame(message.getSender());
                    break;
                case SELECT_USER:
                    selectUserForMultiPlayer(message);
                    break;
                case CHAT:
                    ChatCenter.getInstance().getMessage(message);
                    break;
                case SUDO:
                    sudo(message);
                    break;
                case ADD_CARD:
                    DataCenter.getInstance().addCustomCard(message);
                    break;
                case CHANGE_CARD_NUMBER:
                    DataCenter.getInstance().changeCardNumber(message);
                    break;
                case CHANGE_ACCOUNT_TYPE:
                    DataCenter.getInstance().changeAccountType(message);
                    break;
                case VALIDATE_CARD:
                    DataCenter.getInstance().validateCustomCard(message);
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

    private void sendException(String exceptionString, String receiver, int messageId) {
        addToSendingMessages(Message.makeExceptionMessage(serverName, receiver, exceptionString, messageId));
    }

    private void sendStories(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        addToSendingMessages(Message.makeStoriesCopyMessage(serverName, message.getSender(),
                DataCenter.getInstance().getStories().toArray(Story[]::new), message.getMessageId()));
    }

    private void sendOriginalCards(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        addToSendingMessages(Message.makeOriginalCardsCopyMessage(
                serverName, message.getSender(), DataCenter.getInstance().getOriginalCards(), message.getMessageId()));

    }

    private void sendCustomCards(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        addToSendingMessages(Message.makeCustomCardsCopyMessage(
                serverName, message.getSender(), DataCenter.getInstance().getNewCustomCards()));

    }

    private void sendLeaderBoard(Message message) throws ClientException { //Check
        addToSendingMessages(Message.makeLeaderBoardCopyMessage(serverName, message.getSender(),
                DataCenter.getInstance().getLeaderBoard(), message.getMessageId()));
    }

    private void selectUserForMultiPlayer(Message message) throws ClientException {
        Account account = DataCenter.getInstance().getAccount(message.getNewGameFields().getOpponentUsername());
        if (account == null) {
            throw new ClientException("second player is not valid");
        } else if (!account.hasValidMainDeck()) {
            throw new ClientException("selected deck for second player is not valid");
        } else {
            addToSendingMessages(Message.makeAccountInfoMessage(serverName, message.getSender(), account, message.getMessageId()));
        }
    }

    private void sudo(Message message) {
        String command = message.getOtherFields().getSudoCommand().toLowerCase();
        if (command.contains("account")) {
            for (Account account : DataCenter.getInstance().getAccounts().keySet()) {
                serverPrint(account.getUsername() + " " + account.getPassword());
            }
        }
        addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
    }

    public void sendChangeCardPositionMessage(Game game, Card card, CardPosition newCardPosition) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");//ahmad,please don't change this to exception!
            } else {
                addToSendingMessages(Message.makeChangeCardPositionMessage(
                        serverName, clientName, card, newCardPosition, 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");//ahmad,please don't change this to exception!
            } else {
                addToSendingMessages(Message.makeChangeCardPositionMessage(
                        serverName, clientName, card, newCardPosition, 0));
            }
        }
    }

    public void sendTroopUpdateMessage(Game game, Troop troop) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeTroopUpdateMessage(
                        serverName, clientName, troop, 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");
            } else {
                addToSendingMessages(Message.makeTroopUpdateMessage(
                        serverName, clientName, troop, 0));
            }
        }
    }

    public void sendAttackMessage(Game game, Troop attacker, Troop defender, boolean counterAttack) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeAttackMessage(
                        serverName, clientName, attacker, defender, counterAttack, 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");
            } else {
                addToSendingMessages(Message.makeAttackMessage(
                        serverName, clientName, attacker, defender, counterAttack, 0));
            }
        }
    }

    public void sendGameUpdateMessage(Game game) {
        String clientName;
        List<CellEffect> cellEffects = game.getCellEffects();
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameUpdateMessage(
                        serverName, clientName, game.getTurnNumber(), game.getPlayerOne().getCurrentMP(),
                        game.getPlayerOne().getNumberOfCollectedFlags(), game.getPlayerTwo().getCurrentMP(),
                        game.getPlayerTwo().getNumberOfCollectedFlags(), cellEffects, 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameUpdateMessage(
                        serverName, clientName, game.getTurnNumber(), game.getPlayerOne().getCurrentMP(),
                        game.getPlayerOne().getNumberOfCollectedFlags(), game.getPlayerTwo().getCurrentMP(),
                        game.getPlayerTwo().getNumberOfCollectedFlags(), cellEffects, 0));
            }
        }
    }

    public void sendGameFinishMessages(Game game) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameFinishMessage(
                        serverName, clientName, game.getPlayerOne().getMatchHistory().isAmIWinner(), game.getReward(), 0));
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, clientName, DataCenter.getInstance().getAccount(game.getPlayerOne().getUserName()), 0));
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerTwo().getUserName());
            if (clientName == null) {
                serverPrint("player two has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameFinishMessage(
                        serverName, clientName, game.getPlayerTwo().getMatchHistory().isAmIWinner(), game.getReward(), 0));
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, clientName, DataCenter.getInstance().getAccount(game.getPlayerTwo().getUserName()), 0));
            }
        }
    }

    public void serverPrint(String string) {
        System.out.println("\u001B[32m" + string.trim() + "\u001B[0m");
    }

    public void sendChangeCardNumberMessage(Card card){
        for(Account account:DataCenter.getInstance().getAccounts().keySet()){
            if(account.getAccountType()== AccountType.ADMIN && DataCenter.getInstance().isOnline(account.getUsername())){
                addToSendingMessages(Message.makeChangeCardNumberMessage(serverName,DataCenter.getInstance().getAccounts().get(account),
                        card,card.getRemainingNumber()));
            }
        }
    }

    public void sendLeaderBoardUpdateMessage(Account account){

    }

    public void sendAddToOriginalsMessage(Card card){
        for(Account account:DataCenter.getInstance().getAccounts().keySet()){
            if(account.getAccountType()== AccountType.ADMIN && DataCenter.getInstance().isOnline(account.getUsername())){
                addToSendingMessages(Message.makeAddOriginalCardMessage(serverName,DataCenter.getInstance().getAccounts().get(account),
                        card));
            }
        }
    }

    public void sendAddToCustomCardsMessage(Card card){
        for(Account account:DataCenter.getInstance().getAccounts().keySet()){
            if(account.getAccountType()== AccountType.ADMIN && DataCenter.getInstance().isOnline(account.getUsername())){
                addToSendingMessages(Message.makeAddCustomCardMessage(serverName,DataCenter.getInstance().getAccounts().get(account),
                        card));
            }
        }
    }

    public void sendRemoveCustomCardsMessage(Card card){
        for(Account account:DataCenter.getInstance().getAccounts().keySet()){
            if(account.getAccountType()== AccountType.ADMIN && DataCenter.getInstance().isOnline(account.getUsername())){
                addToSendingMessages(Message.makeRemoveCustomCardMessage(serverName,DataCenter.getInstance().getAccounts().get(account),
                        card.getName()));
            }
        }
    }

    public void sendAccountUpdateMessage(Account account){
        String clientName=DataCenter.getInstance().getAccounts().get(account);
        if(clientName==null)
            return;
        addToSendingMessages(Message.makeAccountCopyMessage(serverName,clientName,account,0));
    }
}