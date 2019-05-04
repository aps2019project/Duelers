package server.models.game;

import server.models.account.Account;
import server.models.card.Deck;
import server.models.map.GameMap;

public class MultiFlagBattle extends Game {
    private int numberOfFlags;

    public MultiFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap, int numberOfFlags) {
        super(accountOne, accountTwo, gameMap,GameType.SOME_FLAG);
        this.numberOfFlags = numberOfFlags;
    }

    public MultiFlagBattle(Account account1, Deck deck, GameMap gameMap,int numberOfFlags) {
        super(account1, deck, gameMap,GameType.SOME_FLAG);
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
