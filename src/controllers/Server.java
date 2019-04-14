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
    private ArrayList<Account> accounts;
    private ArrayList<OnlineSystem> onlineSystems;
    private ArrayList<Game> onlineGames;
    private ArrayList<Deck> customDecks;
    private ArrayList<Account> leaderBoard;
    private ArrayList<Message> sendingMessages;
    private ArrayList<Message> receivingMessages;
    private ArrayList<Story> stories;

    public void StartServer() {
        //read Accounts,customDeck,stories;
    }

    public void addToSendings(Message message) {
        sendingMessages.add(message);
    }

    public void addToReceivings(Message message) {
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

    public void sendGameCopy(System system, Game game) {

    }

    public void sendShopCopy(System system) {

    }

    public void sendAccountCopy(System system, Account account) {

    }

    public void sendStoriesCopy(System system) {

    }

    public void sendCustomDecksCopy(System system) {

    }

    public void sendLeaderBoardCopy(System system) {

    }

    public void sendPositionList(Cell[] cells) {

    }
}