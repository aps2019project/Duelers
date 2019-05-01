package server.models.game;

import server.models.account.Account;
import server.models.map.GameMap;

public class KillHeroBattle extends Game {

    public KillHeroBattle(Account account1, Account account2, GameMap gameMap) {
        super(account1, account2, gameMap);
    }

    @Override
    public void finishCheck() {
        if (getPlayerOne().getHero() == null && getPlayerTwo().getHero() == null) {
            setMatchHistories(true, true);

        } else if (getPlayerOne().getHero() == null) {
            setMatchHistories(false, true);

        } else if (getPlayerTwo().getHero() == null) {
            setMatchHistories(true, false);
        }
    }
}
