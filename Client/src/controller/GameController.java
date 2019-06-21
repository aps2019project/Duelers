package controller;

import models.card.Card;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedTroop;
import models.game.availableActions.AvailableActions;

import java.util.ArrayList;


public class GameController {
    private static GameController ourInstance;
    private CompressedGame currentGame;
    private String selectedItemId;
    private boolean isInGraveYard;
    private String selectedCardId;
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

    public void selectCard(String cardId) {
        CompressedTroop troop = currentGame.getCurrentTurnPlayer().searchTroop(cardId);
        if (troop != null && troop.getPlayerNumber() == currentGame.getCurrentTurnPlayer().getPlayerNumber()) {
            selectedCardId = cardId;
        } else if (currentGame.getCurrentTurnPlayer().searchCollectedItems(cardId) != null) {
            selectedItemId = cardId;
        }
    }

    public void attack(CompressedTroop selectedTroop, CompressedTroop troop) {

    }

    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop troop) {

    }

    public void move(CompressedTroop selectedTroop, int j, int i) {

    }

    public void endTurn(){

    }

    public void insert(String cardID , int row , int column){

    }

    public void useSpecialPower (int row , int column){

    }

}
