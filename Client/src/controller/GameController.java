package controller;

import models.Constants;
import models.card.Card;
import models.card.CardType;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedTroop;
import models.game.GameActions;
import models.game.availableActions.AvailableActions;
import models.game.map.Position;
import models.message.Message;

import java.util.ArrayList;
import java.util.Calendar;


public class GameController implements GameActions {
    private static GameController ourInstance;
    private CompressedGame currentGame;
    private AvailableActions availableActions = new AvailableActions();

    private GameController() {
    }

    public static GameController getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameController();
        }
        return ourInstance;
    }

    public void calculateAvailableActions() {
        availableActions.calculate(currentGame);
    }

    public CompressedGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CompressedGame currentGame) {
        this.currentGame = currentGame;
        currentGame.getPlayerOne().setTroops(currentGame.getGameMap().getPlayerTroop(1));
        currentGame.getPlayerTwo().setTroops(currentGame.getGameMap().getPlayerTroop(2));
    }

    @Override
    public void attack(CompressedTroop selectedTroop, CompressedTroop troop) {

    }

    @Override
    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop troop) {

    }

    @Override
    public void move(CompressedTroop selectedTroop, int j, int i) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void insert(CompressedCard card, int row, int column) {
        if (validatePositionForInsert(card, row, column))
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeInsertMessage(
                            Client.getInstance().getClientName(), Constants.SERVER_NAME, card.getCardId(), new Position(row, column), 0
                    )
            );
    }

    private boolean validatePositionForInsert(CompressedCard card, int row, int column) {
        return (card.getType() == CardType.SPELL || card.getType() == CardType.COLLECTIBLE_ITEM) || (currentGame.getGameMap().getTroop(new Position(row, column)) == null);
    }

    @Override
    public void useSpecialPower(int row, int column) {

    }

}
