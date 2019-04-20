package client.models.card.spell;

public class AvailabilityType {
    private boolean onPut;
    private boolean passive;
    private boolean onAttack;
    private boolean combo;
    private boolean onDeath;
    private boolean continuous;
    private boolean onDefend;
    private boolean specialPower;
    private boolean onStart;

    public AvailabilityType(boolean onPut, boolean passive, boolean onAttack, boolean combo, boolean onDeath, boolean continuous, boolean onDefend, boolean specialPower, boolean onStart) {
        this.onPut = onPut;
        this.passive = passive;
        this.onAttack = onAttack;
        this.combo = combo;
        this.onDeath = onDeath;
        this.continuous = continuous;
        this.onDefend = onDefend;
        this.specialPower = specialPower;
        this.onStart = onStart;
    }

    public boolean isOnPut() {
        return onPut;
    }

    public boolean isPassive() {
        return passive;
    }

    public boolean isOnAttack() {
        return onAttack;
    }

    public boolean isCombo() {
        return combo;
    }

    public boolean isOnDeath() {
        return onDeath;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public boolean isOnDefend() {
        return onDefend;
    }

    public boolean isSpecialPower() {
        return specialPower;
    }

    public boolean isOnStart() {
        return onStart;
    }
}