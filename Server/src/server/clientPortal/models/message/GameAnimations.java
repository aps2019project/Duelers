package server.clientPortal.models.message;


import server.gameCenter.models.map.Position;

import java.util.ArrayList;

public class GameAnimations {
    private ArrayList<CardAnimation> attacks = new ArrayList<>();
    private ArrayList<CardAnimation> counterAttacks = new ArrayList<>();
    private ArrayList<SpellAnimation> spellAnimations = new ArrayList<>();

    void addAttacks(String cardID , String defenderCardID){
        attacks.add(new CardAnimation(cardID , defenderCardID));
    }
    void addCounterAttacks(String cardID , String defenderCardID){
        counterAttacks.add(new CardAnimation(cardID , defenderCardID));
    }

    public void addSpellAnime(String spellID , Position position){
        spellAnimations.add(new SpellAnimation(spellID , position));
    }
}
