package models.message;

import client.models.game.GameType;

public class NewGameFields {
    private GameType gameType;
    private int numberOfFlags;
    private int stage;
    private String customDeckName;
    private String opponentUsername;

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setCustomDeckName(String customDeckName) {
        this.customDeckName = customDeckName;
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }
}
