package client.view;

import client.Client;
import client.models.account.Account;
import client.models.account.AccountInfo;
import client.models.account.Collection;
import client.models.account.MatchHistory;
import client.models.card.Card;
import client.models.card.CardType;
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

    public void showHelp(String help) {
        System.out.println(help);
    }

    public void showLeaderBoard(Client client) {
        int counter = 1;
        for (AccountInfo account : client.getLeaderBoard()) {
            System.out.println(counter + "- UserName : " + account.getUsername() + " - Wins : " + account.getWins());
            counter++;
        }
    }

    public void showDecksList(Account account) {
        int counter = 1;
        System.out.printf("%2d - ", counter++);
        showDeck(account.getMainDeck());
        for (Deck deck : account.getDecks()) {
            if (account.isMainDeck(deck)) continue;
            System.out.printf("%2d - ", counter++);
            showDeck(deck);
        }
    }

    public void showDeck(Deck deck) {
        if (deck == null) return;
        System.out.println(deck.getName() + ":\n");
        System.out.println("Hero:");
        showHero(1, deck.getHero());
        System.out.println("Item:");
        showItem(1, deck.getItem());
        showOtherCards(deck.getOthers());
    }

    public void showUsersList(Client client) {
        System.out.println("users list:");
        for (AccountInfo account : client.getLeaderBoard()) {
            if (client.getAccount().equals(account)) continue;
            System.out.println("-" + account.getUsername());
        }
    }

    public void showGetPasswordMessage() {
        System.out.print("Password: ");
    }

    public void showCardIds(ArrayList<Card> cards) {
        for (Card card : cards) {
            showCardId(card);
        }
    }

    public void showCardId(Card card) {
        System.out.println(card.getCardId());
    }

    public void showDeckValidationMessage(boolean isValid) {
        if (isValid) {
            System.out.println("Deck is valid");
        } else {
            System.out.println("Deck is not valid");
        }
    }

    public void showSuccessfulBuyMessage() {
        System.out.println("Buy successfully done");
    }

    public void showSuccessfulSellMessage() {
        System.out.println("Sell successfully done");
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
        showHeroes(collection.getHeroes());
        showItems(collection.getItems());
        showSpells(collection.getSpells());
        showMinions(collection.getMinions());
    }

    private void showMinions(ArrayList<Card> minions) {
        System.out.println("Minions:\n");
        if (minions.size() == 0) return;
        for (int i = 0; i < minions.size(); i++) {
            Card minion = minions.get(i);
            showMinion(i + 1, minion);
        }
    }

    private void showSpells(ArrayList<Card> spells) {
        System.out.println("Spells:\n");
        if (spells.size() == 0) return;
        for (int i = 0; i < spells.size(); i++) {
            Card spell = spells.get(i);
            showSpell(i + 1, spell);
        }
    }

    private void showItems(ArrayList<Card> items) {
        System.out.println("Items:\n");
        if (items.size() == 0) return;
        for (int i = 0; i < items.size(); i++) {
            Card item = items.get(i);
            showItem(i + 1, item);
        }
    }

    private void showHeroes(ArrayList<Card> heroes) {
        System.out.println("Heroes:\n");
        if (heroes.size() == 0) return;
        for (int i = 0; i < heroes.size(); i++) {
            Card hero = heroes.get(i);
            showHero(i + 1, hero);
        }
    }

    private void showOtherCards(ArrayList<Card> others) {
        System.out.println("Cards:\n");
        if (others.size() == 0) return;
        for (int i = 0; i < others.size(); i++) {
            Card card = others.get(i);
            if (card.getType() == CardType.MINION) {
                showMinion(i + 1, card);
            } else {
                showSpell(i + 1, card);
            }
        }
    }

    private void showMinion(int index, Card minion) {
        System.out.printf("%2d", index);
        System.out.println(" - Name: " + minion.getName() +
                " - Class: " + minion.getAttackType() +
                " - AP: " + minion.getDefaultAp() +
                " - HP: " + minion.getDefaultHp() +
                " - MP: " + minion.getMannaPoint() +
                "\n   - Description: " + minion.getDescription() +
                " - price: " + minion.getPrice() + "\n"
        );
    }

    private void showSpell(int index, Card spell) {
        System.out.printf("%2d", index);
        System.out.println(" - Name: " + spell.getName() +
                " - MP: " + spell.getMannaPoint() +
                "\n   - Description: " + spell.getDescription() +
                " - price: " + spell.getPrice() + "\n"
        );
    }

    private void showItem(int index, Card item) {
        System.out.printf("%2d", index);
        System.out.println(" - Name: " + item.getName() +
                "\n   - Description: " + item.getDescription() +
                " - price: " + item.getPrice() + "\n"
        );
    }

    private void showHero(int index, Card hero) {
        System.out.printf("%2d", index);
        System.out.println(" - Name: " + hero.getName() +
                " - AP: " + hero.getDefaultAp() +
                " - HP: " + hero.getDefaultHp() +
                " - Class: " + hero.getAttackType() +
                "\n   - Description: " + hero.getDescription() +
                " - Price: " + hero.getPrice() + "\n"
        );
    }
}