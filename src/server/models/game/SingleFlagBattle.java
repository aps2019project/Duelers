package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class SingleFlagBattle extends Game {

    public SingleFlagBattle(Account account1, Account account2, GameMap gameMap) {
        super(account1, account2, gameMap);
    }

    @Override
    public void finishCheck() {

    }
}
