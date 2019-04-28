package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class MultiFlagBattle extends MultiPlayerGame {

    public MultiFlagBattle(GameType gameType, Account account1, Account account2, GameMap gameMap) {
        super(gameType, account1, account2, gameMap);
    }

    @Override
    public void finishCheck() {

    }
}
