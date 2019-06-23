package server.clientPortal.models.message;


import server.detaCenter.models.card.Card;

import java.util.ArrayList;

public class GameAnimations {
    private ArrayList<String> attackers = new ArrayList<>();
    private ArrayList<String> defenders = new ArrayList<>();
    private ArrayList<SpellAnimation> spellAnimations = new ArrayList<>();

    public void addAtteacker(String cardID){
        attackers.add(cardID);
    }

    public void addDefender(String cardID){
        defenders.add(cardID);
    }

    public void addSpellAnime(SpellAnimation spellAnimations){

    }

}
