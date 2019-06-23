package models.message;

import models.card.Card;

import java.util.ArrayList;

public class GameAnimations {
    private ArrayList<Card> attackers;
    private ArrayList<Card> defenders;
    private ArrayList<SpellAnimation> spellAnimations;

    public ArrayList<Card> getAttackers() {
        return attackers;
    }

    public ArrayList<Card> getDefenders() {
        return defenders;
    }

    public ArrayList<SpellAnimation> getSpellAnimations() {
        return spellAnimations;
    }
}
