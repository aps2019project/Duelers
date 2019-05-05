package server.models.game.availableActions;

import server.models.game.Troop;
import server.models.map.Position;

import java.util.ArrayList;

public class Move {
    private Troop troop;
    private ArrayList<Position> targets;

    public Move(Troop troop, ArrayList<Position> targets) {
        this.troop = troop;
        this.targets = targets;
    }

    public Troop getTroop() {
        return troop;
    }

    public ArrayList<Position> getTargets() {
        return targets;
    }
}
