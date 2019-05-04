package client.models.game.availableActions;

import client.models.comperessedData.CompressedTroop;
import client.models.map.Position;

import java.util.ArrayList;

public class Move {
    private CompressedTroop troop;
    private ArrayList<Position> targets;

    public Move(CompressedTroop troop, ArrayList<Position> targets) {
        this.troop = troop;
        this.targets = targets;
    }

    public CompressedTroop getTroop() {
        return troop;
    }

    public ArrayList<Position> getTargets() {
        return targets;
    }
}
