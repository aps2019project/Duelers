package client.models.map;

import client.models.game.Troop;

import java.util.ArrayList;

public class GameMap {
    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private ArrayList<Cell> flagCells = new ArrayList<>();
    private ArrayList<Cell> collectibleItemCells = new ArrayList<>();

    public Cell[][] getCells() {
        return this.cells;
    }

    public ArrayList<Troop> getPlayerOneTroops() {
        return this.playerOneTroops;
    }

    public ArrayList<Troop> getPlayerTwoTroops() {
        return this.playerTwoTroops;
    }

    public ArrayList<Cell> getFlagCells() {
        return flagCells;
    }

    public ArrayList<Cell> getCollectibleItemCells() {
        return collectibleItemCells;
    }

    public Troop getTroop(Cell cell) {
        return null;
    }
}