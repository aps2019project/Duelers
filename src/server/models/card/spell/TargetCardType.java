package server.models.card.spell;

public class TargetCardType {
    private boolean cell;
    private boolean hero;
    private boolean minion;

    public TargetCardType(boolean cell, boolean hero, boolean minion) {
        this.cell = cell;
        this.hero = hero;
        this.minion = minion;
    }
}
