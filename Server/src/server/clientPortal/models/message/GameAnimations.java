package server.clientPortal.models.message;


import server.gameCenter.models.map.Position;

import java.util.ArrayList;

public class GameAnimations {
    private ArrayList<CardAnimation> attackers = new ArrayList<>();
    private ArrayList<CardAnimation> defenders = new ArrayList<>();
    private ArrayList<CardAnimation> spellAnimations = new ArrayList<>();

    public void addAtteacker(String cardID , Position position){
        spellAnimations.add(new CardAnimation(cardID , position));
    }

    public void addDefender(String cardID , Position position){
        spellAnimations.add(new CardAnimation(cardID , position));
    }

    public void addSpellAnime(String spellID , Position position){
        spellAnimations.add(new CardAnimation(spellID , position));
    }

}
