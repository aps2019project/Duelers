package models.card.spell;

public class TargetCardType {
    private boolean cell;
    private boolean hero;
    private boolean minion;
    private boolean player;

    public TargetCardType(TargetCardType targetCardType) {
        this.cell = targetCardType.cell;
        this.hero = targetCardType.hero;
        this.minion = targetCardType.minion;
        this.player = targetCardType.player;
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

    public boolean isPlayer() {
        return player;
    }
}
