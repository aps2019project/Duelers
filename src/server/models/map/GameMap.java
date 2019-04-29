package server.models.map;

import server.Server;
import server.models.card.Card;
import server.models.game.Troop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    public static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private ArrayList<Cell> flagCells = new ArrayList<>();
    private ArrayList<Cell> collectibleItemCells = new ArrayList<>();

    public GameMap(HashMap<Cell, Card> items, int numberOfFlags) {
        cells = new Cell[ROW_NUMBER][COLUMN_NUMBER];
        for (Map.Entry<Cell, Card> map : items.entrySet()) {
            if (map.getKey().getRow() < ROW_NUMBER && map.getKey().getColumn() < COLUMN_NUMBER) {
                if (map.getValue() != null) {
                    //cells[map.getKey().getRow()][map.getKey().getColumn()].setItem(map.getValue());
                    //TODO:manage cardIds!
                }
            } else {
                Server.getInstance().serverPrint("Error!");
            }
        }
        //TODO:Generate Keys
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
        for (Troop troop : playerOneTroops) {
            if (troop.getCell() == cell)
                return troop;
        }
        for (Troop troop : playerTwoTroops) {
            if (troop.getCell() == cell)
                return troop;
        }
        return null;
    }

    public boolean hasTroop(Cell cell){
        for (Troop troop : playerOneTroops) {
            if (troop.getCell() == cell)
                return true;
        }
        for (Troop troop : playerTwoTroops) {
            if (troop.getCell() == cell)
                return true;
        }
        return false;
    }

}