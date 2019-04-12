package models.game;

import models.map.Cell;

import java.util.ArrayList;

public class CellEffect {

    private CellEffectType type;
    private int numberOfChanges;
    private int turnsRemaining;
    private ArrayList<Cell> targetCells;


    public CellEffect(CellEffectType type, int changes, ArrayList<Cell> targetCells) {

    }


    public CellEffect(CellEffectType type, ArrayList<Cell> targetCells) {

    }

    public CellEffectType getType() {
        return this.type;
    }

    public int getNumberOfChanges() {
        return this.numberOfChanges;
    }

    public int getTurnsRemaining() {
        return this.turnsRemaining;
    }

    public ArrayList<Cell> getTargetCells() {

        return this.targetCells;
    }

    public void decreaseTurnsRemaining() {

    }

}