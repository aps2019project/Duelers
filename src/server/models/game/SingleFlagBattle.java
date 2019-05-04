package server.models.game;

import server.models.account.Account;
import server.models.card.Deck;
import server.models.map.GameMap;

public class SingleFlagBattle extends Game {

    public SingleFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap) {
        super(accountOne, accountTwo, gameMap, GameType.A_FLAG);
    }

    public SingleFlagBattle(Account account1, Deck deck, GameMap gameMap) {
        super(account1, deck, gameMap, GameType.A_FLAG);
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
