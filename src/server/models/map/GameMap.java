package server.models.map;

import server.models.card.Card;
import server.models.game.Troop;

import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    private static final int ROW_NUMBER = 5, COLUMN_NUMBER = 9;

    private Cell[][] cells;
    private ArrayList<Troop> playerOneTroops = new ArrayList<>();
    private ArrayList<Troop> playerTwoTroops = new ArrayList<>();
    private ArrayList<Cell> flagCells = new ArrayList<>();
    private ArrayList<Cell> collectibleItemCells = new ArrayList<>();

    public GameMap(ArrayList<Card> items, int numberOfFlags, Card originalFlag) {
        cells = new Cell[ROW_NUMBER][COLUMN_NUMBER];
        for (int i = 0; i < ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        cells[0][4].addItem(items.get(new Random(items.size()).nextInt()));
        cells[2][5].addItem(items.get(new Random(items.size()).nextInt()));
        cells[4][4].addItem(items.get(new Random(items.size()).nextInt()));

        for (int i = 0; i < numberOfFlags; i++) {
            int row = new Random(5).nextInt();
            int column = new Random(9).nextInt();
            while (!cells[row][column].getItems().isEmpty()) {
                row = new Random(5).nextInt();
                column = new Random(9).nextInt();
            }
            cells[row][column].addItem(new Card(originalFlag, "Flag", i));
        }

    }

    public static int getRowNumber() {
        return ROW_NUMBER;
    }

    public static int getColumnNumber() {
        return COLUMN_NUMBER;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public ArrayList<Cell> getFlagCells() {
        return flagCells;
    }

    public ArrayList<Cell> getCollectibleItemCells() {
        return collectibleItemCells;
    }

    public ArrayList<Troop> getPlayerOneTroops() {
        return this.playerOneTroops;
    }

    public ArrayList<Troop> getPlayerTwoTroops() {
        return this.playerTwoTroops;
    }

    public void addTroop(int playerNumber, Troop troop) {

    }

    public Troop getTroop(Cell cell) {
        for (Troop troop : playerOneTroops) {
            if (troop.getCell() == cell)
                return troop;
        }
        for (Troop troop : playerTwoTroops) {
            if (troop.getCell() == cell)
                return troop;
        }
        return null;
    }

    public boolean hasTroop(Cell cell) {
        for (Troop troop : playerOneTroops) {
            if (troop.getCell() == cell)
                return true;
        }
        for (Troop troop : playerTwoTroops) {
            if (troop.getCell() == cell)
                return true;
        }
        return false;
    }
}