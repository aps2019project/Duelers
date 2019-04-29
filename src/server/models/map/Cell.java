package server.models.map;

import server.models.card.Card;

public class Cell {

    private int row;
    private int column;
    private Card item;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
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
}