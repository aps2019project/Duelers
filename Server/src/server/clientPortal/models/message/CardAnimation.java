package server.clientPortal.models.message;


import server.gameCenter.models.map.Position;

public class CardAnimation {
    private Position position;
    private String name;

    CardAnimation(String spellID, Position position) {
        this.name = spellID;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
