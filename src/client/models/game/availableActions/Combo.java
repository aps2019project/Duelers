package client.models.game.availableActions;

import client.models.comperessedData.CompressedTroop;

import java.util.ArrayList;

public class Combo {
    private ArrayList<CompressedTroop> attackers = new ArrayList<>();
    private CompressedTroop defenderTroop;

    public Combo(ArrayList<CompressedTroop> attackers, CompressedTroop defenderTroop) {
        this.attackers = attackers;
        this.defenderTroop = defenderTroop;
    }

    public ArrayList<CompressedTroop> getAttackers() {
        return attackers;
    }

    public CompressedTroop getDefenderTroop() {
        return defenderTroop;
    }
}
