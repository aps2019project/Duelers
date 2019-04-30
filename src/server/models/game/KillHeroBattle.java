package server.models.game;

import server.models.account.Account;
import server.models.account.MatchHistory;
import server.models.map.GameMap;

public class KillHeroBattle extends Game {

    public KillHeroBattle(Account account1, Account account2, GameMap gameMap) {
        super(account1, account2, gameMap);
    }

    @Override
    public MatchHistory finishCheck() {
        MatchHistory matchHistory = null;
        if (getPlayerOne().getHero() == null && getPlayerTwo().getHero() == null) {
            finishGame();
            matchHistory = new MatchHistory(
                    getPlayerOne().getUserName(), true, getPlayerTwo().getUserName(), true
            );

        }
        if (getPlayerOne().getHero() == null) {
            finishGame();
            matchHistory = new MatchHistory(
                    getPlayerOne().getUserName(), true, getPlayerTwo().getUserName(), true
            );
        }
        if (getPlayerTwo().getHero() == null) {
            finishGame();
            matchHistory = new MatchHistory(
                    getPlayerOne().getUserName(), true, getPlayerTwo().getUserName(), true
            );
        }
        return matchHistory;
    }
}
