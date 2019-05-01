package server.models.message;

import client.models.card.spell.AvailabilityType;
import client.models.card.spell.Target;

public class CompressedSpell {
    private String spellId;
    private Target target;
    private AvailabilityType availabilityType;
    private int coolDown;
    private int mannaPoint;
    private int lastTurnUsed;

    public CompressedSpell(String spellId, Target target, AvailabilityType availabilityType, int coolDown, int mannaPoint, int lastTurnUsed) {
        this.spellId = spellId;
        this.target = target;
        this.availabilityType = availabilityType;
        this.coolDown = coolDown;
        this.mannaPoint = mannaPoint;
        this.lastTurnUsed = lastTurnUsed;
    }
}
