package server.clientPortal.models.message;

import server.gameCenter.models.map.Position;

public class SpellAnimation {
    String spellID;
    Position position;

    public SpellAnimation(String spellID, Position position) {
        this.spellID = spellID;
        this.position = position;
    }
}
