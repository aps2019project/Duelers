package controllers;

import models.account.Account;
import models.card.Card;
import models.card.Deck;
import models.game.Game;
import models.map.Position;
import models.menus.Menu;

import java.util.ArrayList;

public class System {
    private String systemName;
    private Account account;
    private ArrayList<String> sendingMessages;
    private ArrayList<String> receivingMessages;
    private Game game;
    private ArrayList<Deck> customDecks;
    private ArrayList<Account> leaderBoard;
    private Menu currentMenu;
    private Card selected;
    private ArrayList<Position> positions;

    public void addToSendings(String message) {

    }

    public void addToReceivings(String message) {

    }

    public void receiveMessages() {

    }

    public void sendMessages() {

    }

    public void gameUpdate(String[] message) {

    }

    public void input(String text) {

    }

    public void outPut(String text) {

    }

    public void getGameCopy(String fileName) {

    }

    public void getShopCopy(String fileName) {

    }

    public void getAccountCopy(String fileName) {

    }

    public void getStoriesCopy(String fileName) {

    }

    public void getCustomDecksCopy(String fileName) {

    }

    public void getLeaderBoardCopy(String fileName) {

    }

    public void getPositionList(String fileName) {

    }
}