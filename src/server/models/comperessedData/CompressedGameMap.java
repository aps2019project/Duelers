package server.models.comperessedData;

import server.models.game.Troop;
import server.models.map.Cell;

import java.util.ArrayList;

public class CompressedGameMap {
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private CompressedCell[][] cells = new CompressedCell[ROW_NUMBER][COLUMN_NUMBER];
    private ArrayList<CompressedTroop> troops = new ArrayList<>();

    public CompressedGameMap(Cell[][] cells, ArrayList<Troop> troops) {
        for (int i = 0; i < ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                this.cells[i][j] = cells[i][j].toCompressedCell();
            }
        }
        for (Troop troop : troops) {
            this.troops.add(troop.toCompressedTroop());
        }
    }
}
