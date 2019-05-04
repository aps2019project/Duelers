package client.models.comperessedData;

import client.models.game.GameType;

public class CompressedGame {
    private CompressedPlayer playerOne;
    private CompressedPlayer playerTwo;
    private CompressedGameMap gameMap;
    private int turnNumber;
    private GameType gameType;

    public CompressedPlayer getPlayerOne() {
        return playerOne;
    }

    public CompressedPlayer getPlayerTwo() {
        return playerTwo;
    }

    public CompressedGameMap getGameMap() {
        return gameMap;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public GameType getGameType() {
        return gameType;
    }

    public CompressedPlayer getCurrentTurnPlayer() {
        if (turnNumber % 2 == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public CompressedPlayer getOtherTurnPlayer() {
        if (turnNumber % 2 == 1) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }
}
