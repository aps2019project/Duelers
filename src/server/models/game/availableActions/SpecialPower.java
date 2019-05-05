package server.models.game.availableActions;


import server.models.game.Troop;

public class SpecialPower {
    private Troop hero;

    public SpecialPower(Troop hero) {
        this.hero = hero;
    }

    public Troop getHero() {
        return hero;
    }
}
