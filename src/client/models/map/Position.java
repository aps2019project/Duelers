package client.models.map;

public class Position {
    private int row;
    private int column;

    Position(Cell cell) {
        this.row = cell.getRow();
        this.column = cell.getColumn();
    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "position=[" + row + "," + column + "]";
    }
}