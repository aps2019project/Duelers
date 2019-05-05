package server.models.map;

import server.models.card.Card;
import server.models.comperessedData.CompressedCell;

import java.util.ArrayList;

public class Cell {
    private int row;
    private int column;
    private ArrayList<Card> items = new ArrayList<>();

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public CompressedCell toCompressedCell() {
        return new CompressedCell(row, column, items);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        Cell cell = (Cell) obj;
        return row == cell.row && column == cell.column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }


    public ArrayList<Card> getItems() {
        return items;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(Card item) {
        this.items.add(item);
    }

    public boolean isNextTo(Cell cell) {
        return Math.abs(cell.row - row) < 2 && Math.abs(cell.column - column) < 2;
    }

    public int manhattanDistance(Cell cell) {
        return Math.abs(cell.row - row) + Math.abs(cell.column - column);
    }

    public int manhattanDistance(int selectedRow, int selectedColumn) {
        return Math.abs(selectedRow - this.row) + Math.abs(selectedColumn - this.column);
    }

    public int manhattanDistance(Position position) {
        return Math.abs(position.getRow() - this.row) + Math.abs(position.getColumn() - this.column);

    }
}