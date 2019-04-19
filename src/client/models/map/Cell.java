package client.models.map;

import client.models.card.Card;

public class Cell {
    private int row;
    private int column;
    private Card item;

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Card getItem() {
        return this.item;
    }
}