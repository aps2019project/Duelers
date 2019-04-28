package server.models.map;

import server.models.card.Card;
import server.models.game.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {
    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private ArrayList<Cell> flagCells = new ArrayList<>();
    private ArrayList<Cell> collectibleItemCells = new ArrayList<>();

    public GameMap(HashMap<Cell, Card> items, int numberOfFlags) {

    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public ArrayList<Cell> getFlagCells() {
        return flagCells;
    }

    public ArrayList<Cell> getCollectibleItemCells() {
        return collectibleItemCells;
    }

    public ArrayList<Troop> getPlayerOneTroops() {
        return this.playerOneTroops;
    }

    public ArrayList<Troop> getPlayerTwoTroops() {
        return this.playerTwoTroops;
    }

    public void addTroop(int playerNumber, Troop troop) {

    }

    public Troop getTroop(Cell cell) {
        return null;
    }

    private void createCells() {

    }
}