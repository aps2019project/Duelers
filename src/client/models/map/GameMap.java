package client.models.map;

import client.models.game.Troop;

import java.util.ArrayList;

public class GameMap {
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private Cell[][] cells;
    private ArrayList<Troop> troops = new ArrayList<>();
    private ArrayList<Cell> flagCells = new ArrayList<>();
    private ArrayList<Cell> collectibleItemCells = new ArrayList<>();

    public Cell[][] getCells() {
        return this.cells;
    }

    public boolean checkCoordination(int row, int column) {
        return row >= 0 && row < ROW_NUMBER && column >= 0 && column < COLUMN_NUMBER;
    }

    public Cell getCellWithPosition(Position position) {
        return cells[position.getRow()][position.getColumn()];
    }

    public Cell getCell(int row, int column) {
        if (checkCoordination(row, column)) {
            return cells[row][column];
        }
        return null;
    }

    public Troop getTroop(int row, int column) {
        for (Troop troop : troops) {
            if (troop.getCell().getColumn() == column && troop.getCell().getRow() == row) {
                return troop;
            }
        }
        return null;
    }


    public ArrayList<Cell> getFlagCells() {
        return flagCells;
    }

    public ArrayList<Cell> getCollectibleItemCells() {
        return collectibleItemCells;
    }

}