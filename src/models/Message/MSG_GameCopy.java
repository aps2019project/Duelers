package models.Message;

import models.game.Game;

public class MSG_GameCopy extends Message {
    private Game game;

    public MSG_GameCopy(String sender, String receiver, Game game) {
        super(sender, receiver);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
