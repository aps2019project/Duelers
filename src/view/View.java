package view;

import controllers.Client;
import models.account.Account;
import models.account.MatchHistory;
import models.card.Card;
import models.errors.ErrorType;
import models.game.Game;
import models.game.Player;
import models.map.Cell;

import java.util.ArrayList;

public class View {

    private static View VIEW;

    public static View getInstance() {
        if (VIEW == null) {
            VIEW = new View();
        }
        return VIEW;
    }
    
    public void printError(Exception e){
        System.out.println(e.getMessage());
    }

    public void printError(ErrorType error) {

    }

    public void printAccountHelp() {

    }

    public void printMainMenu() {

    }

    public void printMainMenuHelp() {

    }

    public void printCollectionHelp() {

    }

    public void printShopHelp() {

    }

    public void printGameMenu() {

    }

    public void printStoryMenu() {

    }

    public void printLeaderBoard(Client client) {
        int counter=1;
        for (Account account:client.getLeaderBoard()){
            System.out.println(counter+"- UserName : "+account.getUserName()+" - Wins : "+account.getWins());
            counter++;
        }
    }


    public void printDecksList(Account account) {

    }

    public void printGameModesList() {

    }

    public void printUsersList() {

    }


    public void printGameResults(Game game) {

    }

    public void printGetPasswordMessage() {

    }


    public void PrintCardsList(ArrayList<Card> cards) {

    }


    public void printCardInfo(Card card) {

    }


    public void printCardID(Card card) {

    }


    public void printDeckValidationMessage(boolean isValid) {

    }

    public void printSuccessfulBuyMessage() {

    }

    public void printSuccessfulSellMessage() {

    }

    public void printShopList() {

    }

    public void printGameInfo(MatchHistory game) {

    }

    public void printInMapMinions(Player player) {

    }

    public void printInMapCardInfo(Card card) {

    }

    public void printMoveCardMessage(Card card, Cell targetCell) {

    }

    public void printHand(Player player) {

    }

    public void printCardInsertionMessage(Card card, Cell targetCell) {

    }

    public void printCollectedItems(Player player) {

    }

    public void printGraveYardList(Player player) {

    }

    public void printTurnActionHelp(Player player) {

    }
}