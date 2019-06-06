package models.game.availableActions;

import models.comperessedData.CompressedTroop;

import java.util.ArrayList;

public class Attack {
    private CompressedTroop attackerTroop;
    private ArrayList<CompressedTroop> defenders = new ArrayList<>();

    public Attack(CompressedTroop attackerTroop, ArrayList<CompressedTroop> defenders) {
        this.attackerTroop = attackerTroop;
        this.defenders = defenders;
    }

    public CompressedTroop getAttackerTroop() {
        return attackerTroop;
    }

    public ArrayList<CompressedTroop> getDefenders() {
        return defenders;
    }
}
