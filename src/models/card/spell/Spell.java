package models.card.spell;

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

    public Spell(String spellId, SpellType type, DefiniteType definiteType, TargetBase targetBase, TargetType targetType, AvailabilityType availabilityType, int numberOfChange, int coolDown, int mannaPoint, int duration, Spell carryingSpell) {
        this.spellId = spellId;
        this.type = type;
        this.definiteType = definiteType;
        this.targetBase = targetBase;
        this.targetType = targetType;
        this.availabilityType = availabilityType;
        this.numberOfChange = numberOfChange;
        this.coolDown = coolDown;
        this.mannaPoint = mannaPoint;
        if (duration == -1) {
            this.duration = Integer.MAX_VALUE;
            return;
        }
        this.duration = duration;
        this.carryingSpell = carryingSpell;
    }

    public Spell(String spellId, Spell referenceSpell) {

    }

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

    public int getcoolDown() {
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

    public void addNumberOfUses() {

    }
}