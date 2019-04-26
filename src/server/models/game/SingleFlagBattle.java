package server.models.game;

import server.models.map.Map;

public class SingleFlagBattle extends Game {
    public SingleFlagBattle(GameType gameType, Player playerOne, Player playerTwo, Map map) {
        super(gameType, playerOne, playerTwo, map);
    }

    @Override
    public void finishCheck() {

    }
}
