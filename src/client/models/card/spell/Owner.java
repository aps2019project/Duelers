package client.models.card.spell;

public class Owner {
    private boolean own;
    private boolean enemy;

    public Owner(boolean own, boolean enemy) {
        this.own = own;
        this.enemy = enemy;
    }
}
