package models.comperessedData;

import models.game.map.Position;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompressedGameMap{
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private final CompressedCell[][] cells;
    private final ArrayList<CompressedTroop> troops;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

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

    public List<CompressedTroop> getTroops() {
        return Collections.unmodifiableList(troops);
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

    public List<CompressedTroop> getPlayerTroop(int playerNumber) {
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
                //TODO:fire
                if (cell.getItem() == null) continue;
                if (cell.getItem().getCardId().equalsIgnoreCase(cardId))
                    cell.removeItem();
            }
        }
    }

    public void addFlagNum(Position position, int addition) {
        cells[position.getRow()][position.getColumn()].addNumberOfFlags(addition);
        //TODO:fire
    }

    public int getFlagNum(Position position) {
        return cells[position.getRow()][position.getColumn()].getNumberOfFlags();//TODO:Ahmad Check
    }

    public void updateTroop(CompressedTroop troop) {//flag
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        support.firePropertyChange("troop", getTroop(troop.getCard().getCardId()), troop);
        removeTroop(troop.getCard().getCardId());
        troops.add(troop);
//        cells[troop.getPosition().getRow()][troop.getPosition().getColumn()].removeFlags();
    }

    public void killTroop(String cardId) {//flag
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        for (CompressedTroop troop : troops) {
            if (troop.getCard().getCardId().equalsIgnoreCase(cardId)) {
                addFlagNum(troop.getPosition(), troop.getNumberOfCollectedFlags());
                support.firePropertyChange("troop", troop, null);
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

    public List<CompressedCell> getFlagCells() {
        ArrayList<CompressedCell> flagCells = new ArrayList<>();
        for (CompressedCell[] row : cells) {
            for (CompressedCell cell : row) {
                int number = cell.getNumberOfFlags();
                while (number-- > 0) {
                    flagCells.add(cell);
                }
            }
        }
        return Collections.unmodifiableList(flagCells);
    }
}
