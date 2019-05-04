package client.models.game.availableActions;

import client.models.comperessedData.CompressedCard;

public class Insert {
    private CompressedCard card;

    Insert(CompressedCard card) {
        this.card = card;
    }

    public CompressedCard getCard() {
        return card;
    }
}
