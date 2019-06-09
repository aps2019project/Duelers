package models.comperessedData;

import models.map.Position;

import java.util.ArrayList;

public class CompressedGameMap {
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private CompressedCell[][] cells;
    private ArrayList<CompressedTroop> troops = new ArrayList<>();

    //just for testing BattleView
    public CompressedGameMap(CompressedCell[][] cells, ArrayList<CompressedTroop> troops) {
        this.cells = cells;
        this.troops = troops;
    }

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

    public CompressedTroop searchTroop(String cardID) {
        for (CompressedTroop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardID)) {
                return troop;
            }
        }
        return null;
    }

    public void addTroop(CompressedTroop troop) {
        troops.add(troop);
    }

    public ArrayList<CompressedTroop> getPlayerTroop(int playerNumber) {
        ArrayList<CompressedTroop> compressedTroops = new ArrayList<>();
        for (CompressedTroop troop : troops) {
            if (troop.getPlayerNumber() == playerNumber)
                compressedTroops.add(troop);
        }
        return compressedTroops;
    }

    public CompressedCell getCell(int row, int column) {
        if (isInMap(row, column)) {
            return cells[row][column];
        }

        return null;
    }

    public boolean isInMap(int row, int column) {
        return row >= 0 && row < ROW_NUMBER && column >= 0 && column < COLUMN_NUMBER;

    }

    public CompressedTroop getTroop(Position cell) {
        for (CompressedTroop troop : troops) {
            if (troop.getPosition().equals(cell)) {
                return troop;
            }
        }
        return null;
    }

    public void removeItem(String cardId) {
        for (CompressedCell[] row : cells) {
            for (CompressedCell cell : row) {
                if (cell.getItem() == null) continue;

                if (cell.getItem().getCardId().equalsIgnoreCase(cardId))
                    cell.removeItem();
            }
        }
    }

    public void addFlagNum(Position position, int addition) {
        cells[position.getRow()][position.getColumn()].addNumberOfFlags(addition);
    }

    public int getFlagNum(Position position) {
        return cells[position.getRow()][position.getColumn()].getNumberOfFlags();//TODO:Ahmad Check
    }

    public void updateTroop(CompressedTroop troop) {//flag
        removeTroop(troop.getCard().getCardId());
        troops.add(troop);
        cells[troop.getPosition().getRow()][troop.getPosition().getColumn()].removeFlags();
    }

    public void killTroop(String cardId) {//flag
        for (CompressedTroop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardId)) {
                addFlagNum(troop.getPosition(), troop.getNumberOfCollectedFlags());
            }
        }
        removeTroop(cardId);
    }

    private void removeTroop(String cardId) {
        troops.removeIf(compressedTroop -> compressedTroop.getCard().getCardId().equalsIgnoreCase(cardId));
    }

    public CompressedTroop getTroop(String cardId) {
        for (CompressedTroop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardId)) {
                return troop;
            }
        }
        return null;
    }

    public ArrayList<CompressedCell> getFlagCells() {
        ArrayList<CompressedCell> flagCells = new ArrayList<>();
        for (CompressedCell[] row : cells) {
            for (CompressedCell cell : row) {
                int number = cell.getNumberOfFlags();
                while (number-- > 0) {
                    flagCells.add(cell);
                }
            }
        }
        return flagCells;
    }
}
