package server.models.message;

import server.models.comperessedData.CompressedGame;
import server.models.game.Game;

public class GameCopyMessage {
    CompressedGame compressedGame;

    public GameCopyMessage(Game game) {
        this.compressedGame = game.toCompressedGame();
    }
}
