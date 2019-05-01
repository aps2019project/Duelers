package server.models.game;

import server.models.account.Account;
import server.models.account.MatchHistory;
import server.models.map.GameMap;

public class SingleFlagBattle extends Game {

    public SingleFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap) {
        super(accountOne, accountTwo, gameMap);
    }

    @Override
    public void finishCheck() {
        if (getPlayerOne().getNumberOfCollectedFlags() > 0) {
            setMatchHistories(true, false);
        } else if (getPlayerTwo().getNumberOfCollectedFlags() > 0) {
            setMatchHistories(false, true);
        }
    }
}
