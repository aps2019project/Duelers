package client.models.comperessedData;

import java.util.ArrayList;

public class CompressedGameMap {
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private CompressedCell[][] cells;
    private ArrayList<CompressedTroop> troops = new ArrayList<>();

    public static int getRowNumber() {
        return ROW_NUMBER;
    }

    public static int getColumnNumber() {
        return COLUMN_NUMBER;
    }

    public CompressedCell[][] getCells() {
        return cells;
    }

    public ArrayList<CompressedTroop> getTroops() {
        return troops;
    }
}
