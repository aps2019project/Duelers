package server.models.card.spell;

public class Spell {
    private String spellId;
    private SpellType type;
    private DefiniteType definiteType;
    private Target target;
    private AvailabilityType availabilityType;
    private int numberOfChange;
    private int coolDown;
    private int mannaPoint;
    private int lastTurnUsed;
    private int numberOfUses;
    private int duration;
    private Spell carryingSpell;

    public Spell(String spellId, SpellType type, DefiniteType definiteType, Target target, AvailabilityType availabilityType, int numberOfChange, int coolDown, int mannaPoint, int duration, Spell carryingSpell) {
        this.spellId = spellId;
        this.type = type;
        this.definiteType = definiteType;
        this.target = target;
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

    public Target getTarget() {
        return target;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    public int getDuration() {
        return duration;
    }

    public Spell getCarryingSpell() {
        return carryingSpell;
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

    public void addNumberOfUses() {

    }
}