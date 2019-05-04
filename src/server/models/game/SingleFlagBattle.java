package server.models.game;

import server.models.account.Account;
import server.models.card.Card;
import server.models.card.Deck;
import server.models.exceptions.LogicException;
import server.models.exceptions.ServerException;
import server.models.map.GameMap;

public class SingleFlagBattle extends Game {
    private static final int DEFAULT_COMBO = 6;
    private int currentCombo = -1;

    public SingleFlagBattle(Account accountOne, Account accountTwo, GameMap gameMap) {
        super(accountOne, accountTwo.getMainDeck(),accountTwo.getUsername(), gameMap, GameType.A_FLAG);
    }

    public SingleFlagBattle(Account account1, Deck deck, GameMap gameMap) {
        super(account1, deck,"AI", gameMap, GameType.A_FLAG);
    }

    @Override
    public boolean finishCheck() {
        if (currentCombo >= DEFAULT_COMBO) {
            if (getPlayerOne().getNumberOfCollectedFlags() > 0) {
                setMatchHistories(true, false);
                return true;
            } else if (getPlayerTwo().getNumberOfCollectedFlags() > 0) {
                setMatchHistories(false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void changeTurn(String username) throws LogicException {
        super.changeTurn(username);
        increaseCombo();
    }

    @Override
    void catchFlag(Troop troop, Card item) {
        super.catchFlag(troop, item);
        currentCombo = 0;
    }

    @Override
    void killTroop(Troop troop) throws ServerException {
        if (troop.getFlags().size() > 0) {
            currentCombo = -1;
        }
        super.killTroop(troop);
    }

    private void increaseCombo() {
        if (currentCombo >= 0) {
            currentCombo++;
        }
    }
}
