package client.view;

import client.Client;
import client.models.account.AccountInfo;
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

    public void showShopMenuHelp() {

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
        for (AccountInfo account : client.getLeaderBoard()) {
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
        for (AccountInfo account : client.getLeaderBoard()) {
            if (client.getAccount().equals(account)) continue;
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
        showHeroes(collection);
        showItems(collection);
        showSpells(collection);
        showMinions(collection);
    }

    private void showMinions(Collection collection) {
        System.out.println("Minions:\n");
        ArrayList<Card> minions = collection.getMinions();
        for (int i = 0; i < minions.size(); i++) {
            Card minion = minions.get(i);
            showMinion(i + 1, minion);
        }
    }

    private void showSpells(Collection collection) {
        System.out.println("Spells:\n");
        ArrayList<Card> spells = collection.getSpells();
        for (int i = 0; i < spells.size(); i++) {
            Card spell = spells.get(i);
            showSpell(i + 1, spell);
        }
    }

    private void showItems(Collection collection) {
        System.out.println("Items:\n");
        ArrayList<Card> items = collection.getItems();
        for (int i = 0; i < items.size(); i++) {
            Card item = items.get(i);
            showItem(i + 1, item);
        }
    }

    private void showHeroes(Collection collection) {
        System.out.println("Heroes:\n");
        ArrayList<Card> heroes = collection.getHeroes();
        for (int i = 0; i < heroes.size(); i++) {
            Card hero = heroes.get(i);
            showHero(i + 1, hero);
        }
    }

    private void showMinion(int index, Card minion) {
        System.out.println(index +
                " - Name: " + minion.getName() +
                " - Class: " + minion.getAttackType() +
                " - AP: " + minion.getDefaultAp() +
                " - HP: " + minion.getDefaultHp() +
                " - MP: " + minion.getMannaPoint() +
                " - Description: " + minion.getDescription() +
                " - price: " + minion.getPrice()
        );
    }

    private void showSpell(int index, Card spell) {
        System.out.println(index +
                " - Name: " + spell.getName() +
                " - MP: " + spell.getMannaPoint() +
                " - Description: " + spell.getDescription() +
                " - price: " + spell.getPrice()
        );
    }

    private void showItem(int index, Card item) {
        System.out.println(index +
                " - Name: " + item.getName() +
                " - Description: " + item.getDescription() +
                " - price: " + item.getPrice()
        );
    }

    private void showHero(int index, Card hero) {
        System.out.println(index +
                " - Name: " + hero.getName() +
                " - AP: " + hero.getDefaultAp() +
                " - HP: " + hero.getDefaultHp() +
                " - Class: " + hero.getAttackType() +
                " - Description: " + hero.getDescription() +
                " - Price: " + hero.getPrice()
        );
    }

    public void showCardIds(ArrayList<Card> cards) {
        for (Card card : cards) {
            showCardId(card);
        }
    }
}