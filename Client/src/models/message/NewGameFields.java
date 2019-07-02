package models.message;


import models.game.GameType;

class NewGameFields {
    private GameType gameType;
    private int numberOfFlags;
    private int stage;
    private String customDeckName;
    private String opponentUsername;

    void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    void setStage(int stage) {
        this.stage = stage;
    }

    void setCustomDeckName(String customDeckName) {
        this.customDeckName = customDeckName;
    }

    void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }
}
