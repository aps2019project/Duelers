package models.message;

import models.card.Card;

import java.util.ArrayList;

public class GameAnimations {
    private ArrayList<CardAnimation> attackers;
    private ArrayList<CardAnimation> defenders;
    private ArrayList<CardAnimation> spellAnimations;

    public ArrayList<CardAnimation> getAttackers() {
        return attackers;
    }

    public ArrayList<CardAnimation> getDefenders() {
        return defenders;
    }

    public ArrayList<CardAnimation> getSpellAnimations() {
        return spellAnimations;
    }
}
