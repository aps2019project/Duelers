package models.card.spell;

public class Spell {
    private String spellId;
    private SpellType type;
    private DefiniteType definiteType;
    private TargetBase targetBase;
    private TargetType targetType;
    private AvailabilityType availabilityType;
    private int numberOfChange;
    private int reusePeriod;
    private int mannaPoint;
    private int lastTurnUsed;
    private int numberOfUses;
    private int timeOfEffect;

    public Spell(String spellId, SpellType type, DefiniteType definiteType, TargetBase targetBase, TargetType targetType, AvailabilityType availabilityType, int numberOfChange, int reusePeriod, int mannaPoint, int timeOfEffect) {
        this.spellId = spellId;
        this.type = type;
        this.definiteType = definiteType;
        this.targetBase = targetBase;
        this.targetType = targetType;
        this.availabilityType = availabilityType;
        this.numberOfChange = numberOfChange;
        this.reusePeriod = reusePeriod;
        this.mannaPoint = mannaPoint;
        if (timeOfEffect == -1) {
            this.timeOfEffect = Integer.MAX_VALUE;
            return;
        }
        this.timeOfEffect = timeOfEffect;
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

    public int getReusePeriod() {
        return this.reusePeriod;
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