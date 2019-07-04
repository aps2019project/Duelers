package models.game;

import models.game.map.Position;

public class CellEffect {
    private Position position;
    private boolean positive;

    public Position getPosition() {
        return position;
    }

    public boolean isPositive() {
        return positive;
    }
}
