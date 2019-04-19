package client.models.card.spell;

public class Spell {
    private String spellId;
    private SpellType type;
    private DefiniteType definiteType;
    private TargetBase targetBase;
    private TargetType targetType;
    private AvailabilityType availabilityType;
    private int numberOfChange;
    private int coolDown;
    private int mannaPoint;
    private int lastTurnUsed;
    private int numberOfUses;
    private int duration;
    private Spell carryingSpell;

    public String getSpellId() {
        return this.spellId;
    }

    public SpellType getType() {
        return this.type;
    }

    public DefiniteType getDefiniteType() {
        return this.definiteType;
    }

    public TargetBase getTargetBase() {
        return this.targetBase;
    }

    public int getNumberOfChange() {
        return this.numberOfChange;
    }

    public int getCoolDown() {
        return this.coolDown;
    }

    public int getMannaPoint() {
        return this.mannaPoint;
    }

    public int getLastTurnUsed() {
        return this.lastTurnUsed;
    }

    public void setLastTurnUsed(int turn) {
        this.lastTurnUsed = turn;
    }

    public int getNumberOfUses() {
        return this.numberOfUses;
    }
}