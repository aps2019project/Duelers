package client.models.comperessedData;

public class CompressedCell {
    private int row;
    private int column;
    private CompressedCard item;//non flag item
    private int numberOfFlags;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CompressedCard getItem() {
        return item;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }
}
