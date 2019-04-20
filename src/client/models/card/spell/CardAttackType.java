package client.models.card.spell;

public class CardAttackType {
    private boolean melee;
    private boolean ranged;
    private boolean hybrid;

    public CardAttackType(boolean melee, boolean ranged, boolean hybrid) {
        this.melee = melee;
        this.ranged = ranged;
        this.hybrid = hybrid;
    }
}
