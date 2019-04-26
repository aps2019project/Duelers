package server.models.game;

import server.models.card.Card;
import server.models.card.spell.Spell;
import server.models.map.Cell;
import server.models.map.Map;

import java.util.ArrayList;

public abstract class Game {
    private GameType gameType;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<CellEffect> cellEffects;
    private ArrayList<Buff> buffs;
    private Map map;
    private int turnNumber;
    private int lastTurnChangingTime;

    public Game(GameType gameType, Player playerOne, Player playerTwo, Map map) {

    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public void addCellEffect(CellEffect cellEffect) {

    }

    public void addBuff(Buff buff) {

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

    public void addTurnNum() {

    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public void receiveMessage(String[] message) {

    }

    public void move(Troop troop, Cell targetCell) {

    }

    public void insert(Card card, Cell targetCell) {

    }

    public void attack(Troop attacker, Troop other) {

    }

    public void useSpell(Troop troop, Spell spell, Cell targetCell) {

    }

    public void nextTurn() {

    }

    public abstract void finishCheck();

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