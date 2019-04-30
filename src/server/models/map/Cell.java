package server.models.map;

import server.models.card.Card;

import java.util.ArrayList;

public class Cell {
    private int row;
    private int column;
    private Card item;
    private ArrayList<Card> items = new ArrayList<>();

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
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

    public Card getItem() {
        return this.item;
    }

    public void setItem(Card item) {
        this.item = item;
    }

    public ArrayList<Card> getItems() {
        return items;
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
}