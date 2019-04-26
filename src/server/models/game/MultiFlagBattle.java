package server.models.game;

import server.models.map.Map;

public class MultiFlagBattle extends Game {
    public MultiFlagBattle(GameType gameType, Player playerOne, Player playerTwo, Map map) {
        super(gameType, playerOne, playerTwo, map);
    }

    @Override
    public void finishCheck() {

    }
}
