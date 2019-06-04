package server.clientPortal.models.message;

import server.clientPortal.models.comperessedData.CompressedGame;
import server.gameCenter.models.game.Game;

public class GameCopyMessage {
    CompressedGame compressedGame;

    public GameCopyMessage(Game game) {
        this.compressedGame = game.toCompressedGame();
    }
}
