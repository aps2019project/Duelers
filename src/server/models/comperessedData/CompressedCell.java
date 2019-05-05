package server.models.comperessedData;

import server.models.card.Card;
import server.models.card.CardType;

import java.util.ArrayList;

public class CompressedCell {
    private int row;
    private int column;
    private CompressedCard item;//non flag item
    private int numberOfFlags;

    public CompressedCell(int row, int column, ArrayList<Card> items) {
        this.row = row;
        this.column = column;
        this.numberOfFlags = 0;
        for (Card item : items) {
            if (item.getType() == CardType.FLAG) {
                numberOfFlags++;
            }
            if (item.getType() == CardType.COLLECTIBLE_ITEM) {
                this.item = item.toCompressedCard();
            }
        }
    }


}
