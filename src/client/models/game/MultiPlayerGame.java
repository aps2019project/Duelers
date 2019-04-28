package client.models.game;

import client.models.account.Account;
import client.models.map.GameMap;

public abstract class MultiPlayerGame extends Game {
    private Player playerTwo;

    protected MultiPlayerGame(GameType gameType, Account account1, Account account2, GameMap gameMap) {
        super(gameType, account1, gameMap);

    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
}
