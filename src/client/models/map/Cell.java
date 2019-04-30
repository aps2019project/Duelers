package client.models.map;

import client.models.card.Card;

public class Cell {
    private int row;
    private int column;
    private Card item;

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Card getItem() {
        return this.item;
    }

    public boolean isNextTo(Cell cell) {
        return Math.abs(cell.row - row) < 2 && Math.abs(cell.column - column) < 2;
    }

    public int manhattanDistance(Cell cell) {
        return Math.abs(cell.row - row) + Math.abs(cell.column - column);
    }
    public int manhattanDistance(int selectedRow , int selectedColumn){
        return Math.abs(selectedRow - this.row) + Math.abs(selectedColumn - this.column);

    }
}