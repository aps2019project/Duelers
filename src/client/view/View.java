package client.view;

import client.Client;
import client.models.account.Account;
import client.models.account.Collection;
import client.models.account.MatchHistory;
import client.models.card.Card;
import client.models.card.Deck;
import client.models.game.Game;
import client.models.game.Player;
import client.models.map.Cell;

import java.util.ArrayList;

public class View {

    private static View VIEW;

    public static View getInstance() {
        if (VIEW == null) {
            VIEW = new View();
        }
        return VIEW;
    }

    public void showError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void showMainMenu() {

    }
    public void showShopMenuHelp(){

    }

    public void showHelp(String help) {
        System.out.println(help);
    }


    public void showGameMenu() {

    }

    public void showStoryMenu() {

    }

    public void showLeaderBoard(Client client) {
        int counter = 1;
        for (Account account : client.getLeaderBoard()) {
            System.out.println(counter + "- UserName : " + account.getUsername() + " - Wins : " + account.getWins());
            counter++;
        }
    }

    public void showDecksList(ArrayList<Deck> decks) {
        for (Deck deck : decks) {
            showDeck(deck);
        }
    }

    public void showDeck(Deck deck) {

    }

    public void showGameModesList() {

    }

    public void showUsersList(Client client) {
        System.out.println("users list:");
        for (Account account : client.getLeaderBoard()) {
            if (account.equals(client.getAccount())) continue;
            System.out.println("-" + account.getUsername());
        }
    }

    public void showGameResults(Game game) {

    }

    public void showGetPasswordMessage() {
        System.out.print("Password: ");
    }

    public void showCardsList(ArrayList<Card> cards) {

    }

    public void showCardInfo(Card card) {

    }

    public void showCardId(Card card) {

    }

    public void showDeckValidationMessage(boolean isValid) {
        if (isValid) {
            System.out.println("Deck is valid");
        } else {
            System.out.println("Deck is not valid");
        }
    }

    public void showSuccessfulBuyMessage() {

    }

    public void showSuccessfulSellMessage() {

    }

    public void showGameInfo(MatchHistory game) {

    }

    public void showInMapMinions(Player player) {

    }

    public void showInMapCardInfo(Card card) {

    }

    public void showMoveCardMessage(Card card, Cell targetCell) {

    }

    public void showHand(Player player) {

    }

    public void showCardInsertionMessage(Card card, Cell targetCell) {

    }

    public void showCollectedItems(Player player) {

    }

    public void showGraveYardList(Player player) {

    }

    public void showTurnActionHelp(Player player) {

    }

    public void showCollection(Collection collection) {
        
    }

    public void showCardIds(ArrayList<Card> cards) {
        for (Card card : cards) {
            showCardId(card);
        }
    }
}