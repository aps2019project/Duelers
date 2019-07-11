package models.message;

import models.card.spell.AvailabilityType;
import models.game.map.Position;

public class SpellAnimation {
    private Position[] positions;
    private AvailabilityType availabilityType;

    public Position[] getPositions() {
        return positions;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }
}
