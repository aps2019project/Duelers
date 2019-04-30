package client.models.game;

import client.models.account.Account;
import client.models.card.Card;
import client.models.card.spell.Spell;
import client.models.map.Cell;
import client.models.map.GameMap;
import client.models.map.Position;

public class Game {
    private GameType gameType;
    private Player playerOne;
    private Player playerTwo;
    private GameMap gameMap;
    private int turnNumber;
    private int lastTurnChangingTime;
    private boolean finished = false;

    private Game() {
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getLastTurnChangingTime() {
        return lastTurnChangingTime;
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public boolean isFinished() {
        return finished;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public void toHand(String cardId) {

    }

    public void toNext(String cardId) {

    }

    public void toGraveYard(String cardId) {

    }

    public void toMap(String cardId, Position position) {

    }

    public void toCollectedCards(String cardId, String username) {

    }

    public void moveTroop(String cardId, Position position) {

    }

    public void changeTroopAP(String cardId, int newValue) {

    }

    public void changeTroopHP(String cardId, int newValue) {

    }

    public void changeTurn(String cardId, int newValue) {

    }

    public Troop[] getAttackableTroops() {
        return new Troop[]{};
    }

    public Cell[] getSpellableCells(Troop troop, Spell spell) {
        return new Cell[]{};
    }

    public Cell[] getMovableCells(Troop troop) {
        return new Cell[]{};
    }

    public boolean canAttack(Troop myTroop, Troop enemyTroop) {
        return false;
    }

    public boolean canSpell(Troop myTroop, Cell cell) {
        return false;
    }

    public boolean canInsert(Card card, Cell cell) {
        return false;
    }

    public Player getCurrentTurnPlayer() {
        if (turnNumber % 2 == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public Player getOtherTurnPlayer() {
        if (turnNumber % 2 == 0) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }
}