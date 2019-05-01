package client.models.game;

import client.models.card.spell.AvailabilityType;
import client.models.card.spell.SpellAction;
import client.models.card.spell.Target;

public class CompressedSpell {
    private String spellId;
    private Target target;
    private AvailabilityType availabilityType;
    private int coolDown;
    private int mannaPoint;
    private int lastTurnUsed;
}
