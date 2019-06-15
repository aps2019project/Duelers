package server;

import server.clientPortal.ClientPortal;
import server.detaCenter.DataCenter;
import server.detaCenter.models.account.Account;
import server.detaCenter.models.card.Card;
import server.exceptions.ClientException;
import server.exceptions.LogicException;
import server.exceptions.ServerException;
import server.gameCenter.GameCenter;
import server.gameCenter.models.game.*;
import server.clientPortal.models.message.CardPosition;
import server.clientPortal.models.message.Message;

import java.util.*;

public class Server {
    private static Server server;
    public final String serverName;

    private final Queue<Message> sendingMessages = new LinkedList<>();//TODO:queue
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

    private void receiveMessage(Message message) {//TODO:add fast finish game message
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
                case ADD_TO_DECK:
                    DataCenter.getInstance().addToDeck(message);
                    break;
                case REMOVE_FROM_DECK:
                    DataCenter.getInstance().removeFromDeck(message);
                    break;
                case SELECT_DECK:
                    DataCenter.getInstance().selectDeck(message);
                    break;
                case NEW_MULTIPLAYER_GAME:
                    GameCenter.getInstance().newMultiplayerGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
                    break;
                case NEW_STORY_GAME:
                    GameCenter.getInstance().newStoryGame(message);
                    addToSendingMessages(Message.makeDoneMessage(serverName, message.getSender(), message.getMessageId()));
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

    public void sendGameUpdateMessage(Game game) {
        String clientName;
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            clientName = DataCenter.getInstance().getClientName(game.getPlayerOne().getUserName());
            if (clientName == null) {
                serverPrint("player one has logged out during game!");
            } else {
                addToSendingMessages(Message.makeGameUpdateMessage(
                        serverName, clientName, game.getTurnNumber(), game.getPlayerOne().getCurrentMP(),
                        game.getPlayerOne().getNumberOfCollectedFlags(), game.getPlayerTwo().getCurrentMP(),
                        game.getPlayerTwo().getNumberOfCollectedFlags(), 0));
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
                        game.getPlayerTwo().getNumberOfCollectedFlags(), 0));
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
                        serverName, clientName, game.getPlayerOne().getMatchHistory().isAmIWinner(), 0));
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
                        serverName, clientName, game.getPlayerTwo().getMatchHistory().isAmIWinner(), 0));
                addToSendingMessages(Message.makeAccountCopyMessage(
                        serverName, clientName, DataCenter.getInstance().getAccount(game.getPlayerTwo().getUserName()), 0));
            }
        }
    }


    public String getServerName() {
        return serverName;
    }

    public void serverPrint(String string) {
        System.out.println("\u001B[32m" + string.trim() + "\u001B[0m");
    }
}