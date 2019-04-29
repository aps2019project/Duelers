package server.models.game;

import java.util.ArrayList;

public class Buff {

    private BuffType type;
    private int numberOfChanges;
    private int turnsRemaining;
    private ArrayList<Troop> targetTroops;

    public Buff(BuffType type, int numberOfChanges, int turnsRemaining, ArrayList<Troop> targetTroops) {
        this.type = type;
        this.numberOfChanges = numberOfChanges;
        this.turnsRemaining = turnsRemaining;
        this.targetTroops = targetTroops;
    }

    public Buff(BuffType type, int turnsRemaining, ArrayList<Troop> targetTroops) {
        this.type = type;
        this.turnsRemaining = turnsRemaining;
        this.targetTroops = targetTroops;
    }

    public BuffType getType() {
        return this.type;
    }

    public int getNumberOfChanges() {
        return this.numberOfChanges;
    }

    public int getTurnsRemaining() {
        return this.turnsRemaining;
    }

    public ArrayList<Troop> getTargetTroops() {
        return this.targetTroops;
    }

    public void decreaseTurnsRemaining() {
        turnsRemaining--;
    }
}