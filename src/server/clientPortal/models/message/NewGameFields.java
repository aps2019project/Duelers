package server.clientPortal.models.message;

import client.models.game.GameType;

public class NewGameFields {
    private GameType gameType;
    private int numberOfFlags;
    private int stage;
    private String customDeckName;
    private String opponentUsername;

    public GameType getGameType() {
        return gameType;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getStage() {
        return stage;
    }

    public String getCustomDeckName() {
        return customDeckName;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }
}
