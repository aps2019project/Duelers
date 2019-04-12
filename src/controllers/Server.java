package controllers;

import models.account.Account;
import models.card.Deck;
import models.game.Game;
import models.game.Story;
import models.map.Cell;

import java.util.ArrayList;

public class Server {
    private ArrayList<Account> accounts;
    private ArrayList<OnlineSystem> onlineSystems;
    private ArrayList<Game> onlineGames;
    private ArrayList<Deck> customDecks;
    private ArrayList<Account> leaderBoard;
    private ArrayList<String> sendingMessages;
    private ArrayList<String> receivingMessages;
    private ArrayList<Story> stories;

    public void StartServer() {

    }

    public void addToSendings(String message) {

    }

    public void addToReceivings(String message) {

    }

    public void receiveMessages() {

    }

    public void sendMessages() {

    }

    public void newGame(String[] message) {

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