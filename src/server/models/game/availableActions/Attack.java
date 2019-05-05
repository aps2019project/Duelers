package server.models.game.availableActions;

import server.models.game.Troop;

import java.util.ArrayList;

public class Attack {
    private Troop attackerTroop;
    private ArrayList<Troop> defenders = new ArrayList<>();

    public Attack(Troop attackerTroop, ArrayList<Troop> defenders) {
        this.attackerTroop = attackerTroop;
        this.defenders = defenders;
    }

    public Troop getAttackerTroop() {
        return attackerTroop;
    }

    public ArrayList<Troop> getDefenders() {
        return defenders;
    }
}
