package server.models.game;

import server.models.card.spell.SpellAction;

public class Buff {
    private SpellAction action;
    private TargetData target;

    Buff(SpellAction action, TargetData target) {
        this.action = new SpellAction(action);
        this.target = target;
    }

    public SpellAction getAction() {
        return action;
    }

    public TargetData getTarget() {
        return target;
    }
}