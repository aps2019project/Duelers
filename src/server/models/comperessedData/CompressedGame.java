package server.models.comperessedData;

import server.models.game.Player;
import server.models.map.GameMap;

public class CompressedGame {
    private CompressedPlayer playerOne;
    private CompressedPlayer playerTwo;
    private CompressedGameMap gameMap;
    private int turnNumber;

    public CompressedGame(Player playerOne, Player playerTwo, GameMap gameMap, int turnNumber) {
        this.playerOne = playerOne.toCompressedPlayer();
        this.playerTwo = playerTwo.toCompressedPlayer();
        this.gameMap = gameMap.toCompressedGameMap();
        this.turnNumber = turnNumber;
    }
}
