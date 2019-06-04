package server.gameCenter.models.game;

import server.detaCenter.models.account.Account;
import server.detaCenter.models.card.Deck;
import server.gameCenter.models.map.GameMap;

public class KillHeroBattle extends Game {

    public KillHeroBattle(Account account1, Account account2, GameMap gameMap) {
        super(account1, account2.getMainDeck(), account2.getUsername(), gameMap, GameType.KILL_HERO);
    }

    public KillHeroBattle(Account account1, Deck deck, GameMap gameMap) {
        super(account1, deck, "AI", gameMap, GameType.KILL_HERO);
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
