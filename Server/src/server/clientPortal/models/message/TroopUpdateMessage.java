package server.clientPortal.models.message;

import server.clientPortal.models.comperessedData.CompressedTroop;
import server.gameCenter.models.game.Troop;

public class TroopUpdateMessage {
    CompressedTroop compressedTroop;

    public TroopUpdateMessage(Troop troop) {
        this.compressedTroop = troop.toCompressedTroop();
    }
}
