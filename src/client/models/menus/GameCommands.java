package client.models.menus;

import client.Client;
import client.models.card.AttackType;
import client.models.card.Card;
import client.models.comperessedData.CompressedGame;
import client.models.game.Troop;
import client.models.map.Position;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();
    private CompressedGame currentGame;
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
        String help;
        if (!isInGraveYard) {
            help = "Battle:\n" +
                    "\"Game info\"\n" +
                    "\"Show my minions\"\n" +
                    "\"Show opponent minions\"\n" +
                    "\"Show card info [card id]\"\n" +
                    "\"Select [card id]\"\n";
            if (selectedCardId != null) {
                help += "\"Move to ([x]; [y])\"\n" +
                        "\"Attack [opponent card id]\"\n" +
                        "\"Attack combo [opponent card id] [my card id] [my card id] [...]\"\n" +
                        "\"Use special power (x, y)\"\n";
            }
            help += "\"Show hand\"\n" +
                    "\"Show Next Card\"\n" +
                    "\"Insert [card name] in (x, y)\"\n" +
                    "\"show all decks\"\n" +
                    "\"show deck [deck name]\"\n" +
                    "\"Show collectibles\"\n" +
                    "\"Select [collectible id]\"\n";
            if (selectedItemId != null) {
                help += "\"Show info\"\n" +
                        "\"Use [location x, y]\"\n";
            }
            help += "\"Enter graveyard\"\n" +
                    "\"End turn\"\n" +
                    "\"help\"\n" +
                    "\"exit\"\n";
        } else {
            help = "GraveYard:\n" +
                    "\"Show info [card id]\"\n" +
                    "\"Show cards\"\n" +
                    "\"help\"\n" +
                    "\"exit\"";
        }
        View.getInstance().showHelp(help);
    }

    public void showGameActions() {//help
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


        Troop troop = currentGame.getCurrentTurnPlayer().getTroop(selectedCardId);

        if (!troop.canMove()) {
            throw new InputException("troop can not move");

        }
        if (!currentGame.getGameMap().checkCoordination(row, column)) {
            throw new InputException("coordination is not valid");

        }
        if (currentGame.getGameMap().getTroop(row, column) != null) {
            throw new InputException("cell is not empty");

        }
        if (troop.getCell().manhattanDistance(row, column) > 2) {
            throw new InputException("to far to go");
        }

        Message message = Message.makeMoveTroopMessage(
                client.getClientName(), serverName, selectedCardId, new Position(row, column), 0);
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
        Troop attackerTroop = currentGame.getCurrentTurnPlayer().getTroop(selectedCardId);

        if (!attackerTroop.canAttack()) {
            throw new InputException("attacker can not attack");
        }

        Troop defenderTroop = currentGame.getOtherTurnPlayer().getTroop(oppCardId);
        if (defenderTroop == null) {
            throw new InputException("target card is not valid");
        }

        if (attackerTroop.getCard().getAttackType() == AttackType.MELEE) {
            if (!attackerTroop.getCell().isNextTo(defenderTroop.getCell())) {
                throw new InputException("you can not attack to this target");
            }
        } else if (attackerTroop.getCard().getAttackType() == AttackType.RANGED) {
            if (attackerTroop.getCell().isNextTo(defenderTroop.getCell()) ||
                    attackerTroop.getCell().manhattanDistance(defenderTroop.getCell()) > attackerTroop.getCard().getRange()) {
                throw new InputException("you can not attack to this target");
            }
        } else { // HYBRID
            if (attackerTroop.getCell().manhattanDistance(defenderTroop.getCell()) > attackerTroop.getCard().getRange()) {
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
        View.getInstance().showHand(currentGame.getPlayerOne());
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
                Message.makeInsertMessage(
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

    public void setCurrentGame(CompressedGame currentGame) {
        this.currentGame = currentGame;
    }

    public CompressedGame getCurrentGame() {
        return currentGame;
    }
}
