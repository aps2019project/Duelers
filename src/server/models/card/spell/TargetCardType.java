package server.models.card.spell;

public class TargetCardType {
    private boolean cell;
    private boolean hero;
    private boolean minion;

    public TargetCardType(TargetCardType targetCardType) {
        this.cell = targetCardType.cell;
        this.hero = targetCardType.hero;
        this.minion = targetCardType.minion;
    }

    public TargetCardType(boolean cell, boolean hero, boolean minion) {
        this.cell = cell;
        this.hero = hero;
        this.minion = minion;
    }

    public boolean isCell() {
        return cell;
    }

    public boolean isHero() {
        return hero;
    }

    public boolean isMinion() {
        return minion;
    }
}
