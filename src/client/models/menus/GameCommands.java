package client.models.menus;

import client.Client;
import client.models.game.Game;
import client.models.game.Troop;
import client.models.map.Position;
import client.models.message.Message;
import client.view.View;
import client.models.card.Card;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();
    private Game currentGame;
    private String selectedItem;
    private boolean isInGraveYard;
    private String selectedCardId;

    private GameCommands() {
    }

    public static GameCommands getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {

    }

    @Override
    public void showHelp() {
    }

    public void showGameActions() {

    }

    public void showGameInfo() {
        switch (currentGame.getGameType()) {
            case KILL_HERO:
                View.getInstance().showKillHeroGameInfo(currentGame);
                break;
            case A_FLAG:
                View.getInstance().showOneFlagGameInfo(currentGame);
                break;
            case SOME_FLAG:
                View.getInstance().showMultiFlagGameInfo(currentGame);
                break;
        }
    }

    public void showMyMinions() {
        View.getInstance().showTroops(currentGame.getCurrentTurnPlayer());
    }

    public void showOppMinions() {
        View.getInstance().showTroops(currentGame.getOtherTurnPlayer());
    }

    public void showCardInfo(String cardId) {
        Troop troop = currentGame.getCurrentTurnPlayer().searchTroop(cardId);
        if (troop != null) {
            View.getInstance().showTroopInfo(troop);
            return;
        }
        Card card = currentGame.getCurrentTurnPlayer().searchCard(cardId);
        if (card != null) {
            View.getInstance().showCardInfo(card);
            return;
        }
    }

    public void selectCard(String cardId) {
        selectedCardId = cardId;
    }

    public void move(Client client, String serverName, int row, int column) {
        Message message = Message.makeMoveTroopMessage(
                client.getClientName(), serverName, selectedCardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

    }

    public void attack(Client client, String severName, String oppCardId) {
        Message message = Message.makeAttackMessage(
                client.getClientName(), severName, selectedCardId, oppCardId, 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void attackCombo(Client client, String serverName, String oppCardId, String[] cardIds) {
        Message message = Message.makeComboAttackMessage(
                client.getClientName(), serverName, oppCardId, cardIds, 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void useSpecialPower(Client client, String serverName, int row, int column) {
        Message message = Message.makeUseSpecialPowerMessage(
                client.getClientName(), serverName, selectedCardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

    }

    public void showHand(Client client) {
        View.getInstance().showHand(client.getGame().getPlayerOne());
    }

    public void insertCard(Client client, String serverName, String cardId, int row, int column) {
        Message message = Message.makeInsertMessage(
                client.getClientName(), serverName, cardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void endTurn(Client client, String serverName) {
        Message message = Message.makeEndTurnMessage(client.getClientName(), serverName, 0);
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void showCollectibleItems() {

    }

    public void selectItem(String itemID) {
        selectedItem = itemID;
    }

    public void showNextCard() {

    }

    public boolean isItemSelected() {
        return selectedItem != null;
    }

    public void showSelectedItemInfo() {

    }

    public void useItem(Client client, String serverName, int row, int column) {
        Message message = Message.useItem(client.getClientName(), serverName, selectedItem, new Position(row, column), 0);
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public boolean isInGraveYard() {
        return isInGraveYard;
    }

    public void enterGraveYard() {

    }

    public void showCardInfoInGraveYard(String cardId) {

    }

    public void exitFromGraveYard() {
        isInGraveYard = false;
    }

    public void showCardsInGraveYard() {

    }

    public void endGame() {

    }

    public boolean isAnyCardSelected() {
        return selectedCardId != null;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
