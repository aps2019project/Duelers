package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class SingleFlagBattle extends Game {

    public SingleFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap) {
        super(accountOne, accountTwo, gameMap);
    }

    @Override
    public boolean finishCheck() {
        if (getPlayerOne().getNumberOfCollectedFlags() > 0) {
            setMatchHistories(true, false);
            return true;
        } else if (getPlayerTwo().getNumberOfCollectedFlags() > 0) {
            setMatchHistories(false, true);
            return true;
        }
        return false;
    }
}
