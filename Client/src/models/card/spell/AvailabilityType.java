package models.card.spell;

public class AvailabilityType {
    private boolean onPut;
    private boolean onAttack;
    private boolean onDeath;
    private boolean continuous;
    private boolean specialPower;
    private boolean onStart;
    private boolean onDefend;

    public AvailabilityType(boolean onPut, boolean onAttack, boolean onDeath, boolean continuous, boolean specialPower, boolean onStart) {
        this.onPut = onPut;
        this.onAttack = onAttack;
        this.onDeath = onDeath;
        this.specialPower = specialPower;
        this.onStart = onStart;
        this.continuous = continuous;
    }
}