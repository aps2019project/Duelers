package models.message;

import java.util.Collections;
import java.util.List;

public class GameAnimations {
    private List<CardAnimation> attackers;
    private List<CardAnimation> defenders;
    private List<CardAnimation> spellAnimations;

    public List<CardAnimation> getAttackers() {
        return Collections.unmodifiableList(attackers);
    }

    public List<CardAnimation> getDefenders() {
        return Collections.unmodifiableList(defenders);
    }

    public List<CardAnimation> getSpellAnimations() {
        return Collections.unmodifiableList(spellAnimations);
    }
}
