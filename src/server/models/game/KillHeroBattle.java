package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class KillHeroBattle extends Game {

    public KillHeroBattle(Account account1, Account account2, GameMap gameMap) {
        super(account1, account2, gameMap);
    }

    public KillHeroBattle(Account account1 , Story story,GameMap gameMap){
        super(account1,story,gameMap);
    }

    @Override
    public boolean finishCheck() {
        if (getPlayerOne().getHero() == null && getPlayerTwo().getHero() == null) {
            setMatchHistories(true, true);
            return true;

        }
        if (getPlayerOne().getHero() == null) {
            setMatchHistories(false, true);
            return true;

        }
        if (getPlayerTwo().getHero() == null) {
            setMatchHistories(true, false);
            return true;
        }
        return false;
    }
}
