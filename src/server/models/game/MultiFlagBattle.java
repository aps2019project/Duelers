package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class MultiFlagBattle extends Game {
    private int numberOfFlags;

    public MultiFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap, int numberOfFlags) {
        super(accountOne, accountTwo, gameMap);
        this.numberOfFlags = numberOfFlags;
    }

    @Override
    public boolean finishCheck() {
        if (getPlayerOne().getNumberOfCollectedFlags() >= numberOfFlags / 2) {
            setMatchHistories(true, false);
            return true;
        }
        if (getPlayerTwo().getNumberOfCollectedFlags() >= numberOfFlags / 2) {
            setMatchHistories(false, true);
            return true;
        }
        return false;
    }
}
