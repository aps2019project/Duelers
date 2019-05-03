package client.models.message;

import client.models.comperessedData.CompressedTroop;
import client.models.game.Troop;

public class TroopUpdateMessage {
    CompressedTroop compressedTroop;

    public CompressedTroop getCompressedTroop() {
        return compressedTroop;
    }
}
