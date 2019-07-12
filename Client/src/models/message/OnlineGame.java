package models.message;

import models.game.GameType;

public class OnlineGame {
    private String player1;
    private String player2;
    private GameType gameType;

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public GameType getGameType() {
        return gameType;
    }
}
