package client.models.card.spell;

public class AvailabilityType {
    private boolean onPut;
    private boolean onAttack;
    private boolean onDeath;
    private boolean continuous;
    private boolean specialPower;
    private boolean onStart;

    public AvailabilityType(boolean onPut, boolean onAttack, boolean onDeath, boolean continuous, boolean specialPower, boolean onStart) {
        this.onPut = onPut;
        this.onAttack = onAttack;
        this.onDeath = onDeath;
        this.continuous = continuous;
        this.specialPower = specialPower;
        this.onStart = onStart;
    }

    public boolean isOnPut() {
        return onPut;
    }

    public boolean isOnAttack() {
        return onAttack;
    }

    public boolean isOnDeath() {
        return onDeath;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public boolean isSpecialPower() {
        return specialPower;
    }

    public boolean isOnStart() {
        return onStart;
    }
}