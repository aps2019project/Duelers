package server.models.game.availableActions;

import server.models.game.Troop;

import java.util.ArrayList;

public class Combo {
    private ArrayList<Troop> attackers = new ArrayList<>();
    private Troop defenderTroop;

    public Combo(ArrayList<Troop> attackers, Troop defenderTroop) {
        this.attackers = attackers;
        this.defenderTroop = defenderTroop;
    }

    public ArrayList<Troop> getAttackers() {
        return attackers;
    }

    public Troop getDefenderTroop() {
        return defenderTroop;
    }
}
