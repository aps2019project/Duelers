package client.models.menus;

import client.Client;
import client.models.card.AttackType;
import client.models.comperessedData.CompressedCard;
import client.models.comperessedData.CompressedGame;
import client.models.comperessedData.CompressedTroop;
import client.models.game.availableActions.AvailableActions;
import client.models.map.Position;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class GameCommands extends Menu {
    private static GameCommands ourInstance;
    private CompressedGame currentGame;
    private String selectedItemId;
    private boolean isInGraveYard;
    private String selectedCardId;
    private AvailableActions availableActions = new AvailableActions();


    private GameCommands() {
    }

    public static GameCommands getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameCommands();
        }
        return ourInstance;
    }

    public void calculateAvailableActions() {
        availableActions.calculate(currentGame);
    }

    @Override
    public void exit(Client client) {
        isInGraveYard = false;
        selectedItemId = null;
        selectedCardId = null;
    }

    @Override
    public void showHelp() {
        String help;
        if (!isInGraveYard) {
            help = "Battle:\n" +
                    "\"Game info\"\n" +
                    "\"Show my minions\"\n" +
                    "\"Show opponent minions\"\n" +
                    "\"Show card info [card id]\"\n" +
                    "\"Select [card id]\"\n";
            if (selectedCardId != null) {
                help += "\"Move to ([x], [y])\"\n" +
                        "\"Attack [opponent card id]\"\n" +
                        "\"Attack combo [opponent card id] [my card id] [my card id] [...]\"\n" +
                        "\"Use special power (x, y)\"\n";
            }
            help += "\"Show hand\"\n" +
                    "\"Show Next Card\"\n" +
                    "\"Insert [card id] in (x, y)\"\n" +
                    "\"Show collectibles\"\n" +
                    "\"Select [collectible id]\"\n";
            if (selectedItemId != null) {
                help += "\"Show info\"\n" +
                        "\"Use (x, y)\"\n";
            }
            help += "\"Enter graveyard\"\n" +
                    "\"End turn\"\n" +
                    "\"help\"\n" +
                    "\"Show menu\"\n" +
                    "\"exit\"\n" +
                    "\"end game\"\n";
        } else {
            help = "GraveYard:\n" +
                    "\"Show info [card id]\"\n" +
                    "\"Show cards\"\n" +
                    "\"help\"\n" +
                    "\"exit\"";
        }
        View.getInstance().showHelp(help);
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public CompressedGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CompressedGame currentGame) {
        this.currentGame = currentGame;
        currentGame.getPlayerOne().setTroops(currentGame.getGameMap().getPlayerTroop(1));
        currentGame.getPlayerTwo().setTroops(currentGame.getGameMap().getPlayerTroop(2));
        calculateAvailableActions();
    }

    public void showGameActions() { //help
        View.getInstance().showAvailableActions(currentGame, availableActions);
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
        View.getInstance().showTroops(currentGame.getCurrentTurnPlayer().getTroops());
    }

    public void showOppMinions() {
        View.getInstance().showTroops(currentGame.getOtherTurnPlayer().getTroops());
    }

    public void showCardInfo(String cardId) throws InputException {
        CompressedTroop troop = currentGame.getGameMap().searchTroop(cardId);
        if (troop != null) {
            View.getInstance().showTroopInfo(troop);
            return;
        }
        CompressedCard card = currentGame.getCurrentTurnPlayer().searchHand(cardId);
        if (card != null) {
            View.getInstance().showCardInfo(card);
            return;
        }
        throw new InputException("card id is not valid");
    }

    public void selectCard(String cardId) throws InputException {
        CompressedTroop troop = currentGame.getGameMap().searchTroop(cardId);
        if (troop != null && troop.getPlayerNumber() == currentGame.getCurrentTurnPlayer().getPlayerNumber()) {
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

        CompressedTroop troop = currentGame.getGameMap().searchTroop(selectedCardId);
        Position target = new Position(row, column);
        if (!troop.canMove()) {
            throw new InputException("troop can not move");

        }
        if (!currentGame.getGameMap().isInMap(row, column)) {
            throw new InputException("coordination is not valid");

        }
        if (currentGame.getGameMap().getTroop(new Position(row, column)) != null) {
            throw new InputException("cell is not empty");

        }
        if (troop.getPosition().manhattanDistance(row, column) > 2) {
            throw new InputException("to far to go");
        }

        Message message = Message.makeMoveTroopMessage(
                client.getClientName(), serverName, selectedCardId, target, 0);
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedCardId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        View.getInstance().showMoveCardMessage(troop, target);
        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void attack(Client client, String severName, String oppCardId) throws InputException {
        if (selectedCardId == null) {
            throw new InputException("select a card");
        }
        CompressedTroop attackerTroop = currentGame.getCurrentTurnPlayer().searchTroop(selectedCardId);

        if (!attackerTroop.canAttack()) {
            throw new InputException("attacker can not attack");
        }
        CompressedTroop defenderTroop = currentGame.getOtherTurnPlayer().searchTroop(oppCardId);

        if (defenderTroop == null) {
            throw new InputException("target card is not valid");
        }

        if (attackerTroop.getCard().getAttackType() == AttackType.MELEE) {
            if (!attackerTroop.getPosition().isNextTo(defenderTroop.getPosition())) {
                throw new InputException("you can not attack to this target");
            }
        } else if (attackerTroop.getCard().getAttackType() == AttackType.RANGED) {
            if (attackerTroop.getPosition().isNextTo(defenderTroop.getPosition()) ||
                    attackerTroop.getPosition().manhattanDistance(defenderTroop.getPosition()) > attackerTroop.getCard().getRange()) {
                throw new InputException("you can not attack to this target");
            }
        } else { // HYBRID
            if (attackerTroop.getPosition().manhattanDistance(defenderTroop.getPosition()) > attackerTroop.getCard().getRange()) {
                throw new InputException("you can not attack to this target");
            }
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

        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void attackCombo(Client client, String serverName, String oppCardId, String[] cardIds) throws InputException { // TODO: check validation
        Message message = Message.makeComboAttackMessage(
                client.getClientName(), serverName, oppCardId, cardIds, 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
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

        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void showHand() {
        View.getInstance().showHand(currentGame.getPlayerOne());
    }

    public void insertCard(Client client, String serverName, String cardId, int row, int column) throws InputException {
        CompressedCard card = currentGame.getCurrentTurnPlayer().searchHand(cardId);
        Position target = new Position(row, column);
        Message message = Message.makeInsertMessage(
                client.getClientName(), serverName, cardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        View.getInstance().showCardInsertionMessage(card, target);
        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void endTurn(Client client, String serverName) throws InputException {
        Message message = Message.makeEndTurnMessage(client.getClientName(), serverName, 0);
        client.addToSendingMessages(message);
        client.sendMessages();
        selectedItemId = null;
        selectedCardId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void showCollectibleItems() {
        View.getInstance().showCollectedItems(currentGame.getCurrentTurnPlayer());
    }

    public void showSelectedItemInfo() throws InputException {
        if (selectedItemId == null) {
            throw new InputException("select an item");
        }

        CompressedCard item = currentGame.getCurrentTurnPlayer().searchCollectedItems(selectedItemId);
        View.getInstance().showItemInfo(item);
        selectedItemId = null;
    }

    public void useItem(Client client, String serverName, int row, int column) throws InputException {
        if (selectedItemId == null) {
            throw new InputException("select an item");
        }

        client.addToSendingMessages(
                Message.makeInsertMessage(
                        client.getClientName(), serverName, selectedItemId, new Position(row, column), 0
                )
        );
        client.sendMessages();
        selectedItemId = null;

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        calculateAvailableActions();
        View.getInstance().showMap(currentGame.getGameMap());
    }

    public void showNextCard() throws InputException {
        CompressedCard nextCard = currentGame.getCurrentTurnPlayer().getNextCard();
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

        for (CompressedCard card : currentGame.getCurrentTurnPlayer().getGraveyard()) {
            View.getInstance().showCardInfo(card);
        }
    }

    public void showCardInfoInGraveYard(String cardId) throws InputException {
        if (!isInGraveYard) {
            throw new InputException("you are not in graveyard");
        }

        CompressedCard card = currentGame.getCurrentTurnPlayer().searchGraveyard(cardId);
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
        //TODO: both clients will moveTroop to main menu.
        //TODO:
        throw new InputException("game is not finished.");
    }

}

