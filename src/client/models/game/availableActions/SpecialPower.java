package client.models.game.availableActions;

import client.models.comperessedData.CompressedTroop;

public class SpecialPower {
    private CompressedTroop hero;

    public SpecialPower(CompressedTroop hero) {
        this.hero = hero;
    }

    public CompressedTroop getHero() {
        return hero;
    }
}
