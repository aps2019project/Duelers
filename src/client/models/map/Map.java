package client.models.map;

import client.models.game.Troop;

import java.util.ArrayList;

public class Map {
    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private int numberOfFlags;

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

    public Troop getTroop(Cell cell) {
        return null;
    }
}