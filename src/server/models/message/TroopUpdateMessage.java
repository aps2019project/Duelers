package server.models.message;

import server.models.comperessedData.CompressedTroop;
import server.models.game.Troop;

public class TroopUpdateMessage {
    CompressedTroop compressedTroop;

    public TroopUpdateMessage(Troop troop) {
        this.compressedTroop = troop.toCompressedTroop();
    }
}
