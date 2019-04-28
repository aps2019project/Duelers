package server.models.map;

import server.models.card.Card;
import server.models.game.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {

    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private int numberOfFlags;

    public GameMap(HashMap<Cell, Card> items, int numberOfFlags) {

    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public ArrayList<Troop> getPlayerOneTroops() {
        return this.playerOneTroops;
    }

    public ArrayList<Troop> getPlayerTwoTroops() {
        return this.playerTwoTroops;
    }

    public int getNumberOfFlags() {
        return this.numberOfFlags;
    }

    public void addTroop(int playerNumber, Troop troop) {

    }

    public Troop getTroop(Cell cell) {
        return null;
    }

    private void createCells() {

    }
}