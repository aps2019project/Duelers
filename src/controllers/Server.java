package controllers;

import models.message.Message;
import models.account.Account;
import models.card.Deck;
import models.game.Game;
import models.game.GameType;
import models.game.Player;
import models.game.Story;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;

public class Server {
    private String serverName;
    private ArrayList<Account> accounts;
    private ArrayList<onlineClient> onlineClients;
    private ArrayList<Game> onlineGames;
    private ArrayList<Deck> customDecks;
    private ArrayList<Account> leaderBoard;
    private ArrayList<Message> sendingMessages;
    private ArrayList<Message> receivingMessages;
    private ArrayList<Story> stories;
    
    public String getServerName() {
        return serverName;
    }
    
    public Server(String serverName) {
        //read Accounts,customDeck,stories;
        this.serverName=serverName;
    }

    public void addToSendings(Message message) {
        sendingMessages.add(message);
    }

    public void addToReceivingMessages(Message message) {
        receivingMessages.add(message);
    }

    public void receiveMessages() {
    
    }

    public void sendMessages() {

    }

    public void newGame(GameType gameType, Player playerOne, Player playerTwo, Map map) {
        onlineGames.add(new Game(gameType, playerOne, playerTwo, map));
        //...
    }

    public void sendGameCopy(Client client, Game game) {

    }

    public void sendShopCopy(Client client) {

    }

    public void sendAccountCopy(Client client, Account account) {

    }

    public void sendStoriesCopy(Client client) {

    }

    public void sendCustomDecksCopy(Client client) {

    }

    public void sendLeaderBoardCopy(Client client) {

    }

    public void sendPositionList(Cell[] cells) {

    }
}