package client.view;

import client.Client;
import client.models.account.Account;
import client.models.account.MatchHistory;
import client.models.card.Card;
import client.models.game.Game;
import client.models.game.Player;
import client.models.map.Cell;
import client.models.menus.AccountMenu;
import client.models.menus.BattleMenu;
import client.models.menus.MainMenu;

import java.util.ArrayList;

public class View {

    private static View VIEW;

    public static View getInstance() {
        if (VIEW == null) {
            VIEW = new View();
        }
        return VIEW;
    }

    public void printBattleMenuHelp(){
        System.out.println(BattleMenu.getInstance().getHelp());
    }
    public void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void printAccountHelp() {
        System.out.println(AccountMenu.getInstance().getAccountMenuHelp());
    }

    public void printMainMenu() {

    }

    public void printMainMenuHelp() {
        System.out.println(MainMenu.getInstance().getHelp());
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
        int counter = 1;
        for (Account account : client.getLeaderBoard()) {
            System.out.println(counter + "- UserName : " + account.getUserName() + " - Wins : " + account.getWins());
            counter++;
        }
    }

    public void printDecksList(Account account) {

    }

    public void printGameModesList() {

    }

    public void printUsersList(Client client) {
        for (Account account : client.getLeaderBoard()) {
            System.out.println("UserName" + account.getUserName());
        }
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