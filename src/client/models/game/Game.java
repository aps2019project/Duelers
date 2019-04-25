package client.models.game;

import client.models.card.Card;
import client.models.card.spell.Spell;
import client.models.map.Cell;
import client.models.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private GameType gameType;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<CellEffect> cellEffects;
    private ArrayList<Buff> buffs;
    private Map map;
    private int turnNumber;
    private int lastTurnChangingTime;
    private HashMap<String, Integer> attacksOfGladiators;

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public ArrayList<Buff> getBuffs() {
        return this.buffs;
    }

    public ArrayList<CellEffect> getCellEffects() {
        return this.cellEffects;
    }

    public Map getMap() {
        return this.map;
    }

    public int getTurnNumber() {
        return this.turnNumber;
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
}