package client.models.menus;

import client.Client;
import client.models.game.Game;
import client.models.game.Troop;
import client.models.map.Position;
import client.models.message.Message;
import client.view.View;
import client.models.card.Card;
import client.view.request.InputException;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();
    private Game currentGame;
    private String selectedItemId;
    private boolean isInGraveYard;
    private String selectedCardId;

    private GameCommands() {
    }

    public static GameCommands getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    @Override
    public void showHelp() {
        // TODO help of each part
    }

    public void showGameActions() {
        // TODO all of actions
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

    public void showCardInfo(String cardId) throws InputException {
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
        throw new InputException("card id is not valid");
    }

    public void selectCard(String cardId) throws InputException {
        if (currentGame.getCurrentTurnPlayer().searchTroop(cardId) != null) {
            selectedCardId = cardId;
        } else if (currentGame.getCurrentTurnPlayer().searchCollectedItems(cardId) != null) {
            selectedItemId = cardId;
        } else {
            throw new InputException("card id is not valid");
        }
    }

    public void move(Client client, String serverName, int row, int column) throws InputException {
        if (selectedCardId == null) {
            throw new InputException("select a card");
        }

        Message message = Message.makeMoveTroopMessage(
                client.getClientName(), serverName, selectedCardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedCardId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void attack(Client client, String severName, String oppCardId) throws InputException {
        if (selectedCardId == null) {
            throw new InputException("select a card");
        }

        Message message = Message.makeAttackMessage(
                client.getClientName(), severName, selectedCardId, oppCardId, 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedCardId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void attackCombo(Client client, String serverName, String oppCardId, String[] cardIds) throws InputException {
        Message message = Message.makeComboAttackMessage(
                client.getClientName(), serverName, oppCardId, cardIds, 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void useSpecialPower(Client client, String serverName, int row, int column) throws InputException {
        if (selectedCardId == null) {
            throw new InputException("select a card");
        }

        Message message = Message.makeUseSpecialPowerMessage(
                client.getClientName(), serverName, selectedCardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedCardId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void showHand(Client client) {
        View.getInstance().showHand(client.getGame().getPlayerOne());
    }

    public void insertCard(Client client, String serverName, String cardId, int row, int column) throws InputException {
        Message message = Message.makeInsertMessage(
                client.getClientName(), serverName, cardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
        //TODO: message should be printed in client.receiveMessages
    }

    public void endTurn(Client client, String serverName) {
        Message message = Message.makeEndTurnMessage(client.getClientName(), serverName, 0);
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedItemId = null;
        selectedCardId = null;
    }

    public void showCollectibleItems() {
        View.getInstance().showCollectedItems(currentGame.getCurrentTurnPlayer());
    }

    public void showSelectedItemInfo() throws InputException {
        if (selectedItemId == null) {
            throw new InputException("select an item");
        }

        Card item = currentGame.getCurrentTurnPlayer().searchCollectedItems(selectedItemId);
        View.getInstance().showItemInfo(item);
        selectedItemId = null;
    }

    public void useItem(Client client, String serverName, int row, int column) throws InputException {
        if (selectedItemId == null) {
            throw new InputException("select an item");
        }

        client.addToSendingMessages(
                Message.useItem(
                        client.getClientName(), serverName, selectedItemId, new Position(row, column), 0
                )
        );
        client.sendMessages();
        selectedItemId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void showNextCard() throws InputException {
        Card nextCard = currentGame.getCurrentTurnPlayer().getNextCard();
        if (nextCard == null) {
            throw new InputException("no cards remaining");
        }
        View.getInstance().showCardInfo(nextCard);
    }

    public void enterGraveYard() throws InputException {
        if (isInGraveYard) {
            throw new InputException("already in graveyard");
        }
        isInGraveYard = true;
    }

    public void showCardsInGraveYard() throws InputException {
        if (!isInGraveYard) {
            throw new InputException("you are not in graveyard");
        }

        for (Card card : currentGame.getCurrentTurnPlayer().getGraveyard()) {
            View.getInstance().showCardInfo(card);
        }
    }

    public void showCardInfoInGraveYard(String cardId) throws InputException {
        if (!isInGraveYard) {
            throw new InputException("you are not in graveyard");
        }

        Card card = currentGame.getCurrentTurnPlayer().searchGraveyard(cardId);
        if (card == null) {
            throw new InputException("this card is not in graveyard");
        }

        View.getInstance().showCardInfo(card);
    }

    public void exitFromGraveYard() {
        isInGraveYard = false;
    }

    public boolean isInGraveYard() {
        return isInGraveYard;
    }

    public void endGame() throws InputException {
        if (!currentGame.isFinished()) {
            throw new InputException("game is not finished.");
        }
        //TODO: both clients will moveTroop to main menu.
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
