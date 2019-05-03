package client.models.message;

import client.models.card.Card;
import client.models.comperessedData.CompressedCard;

public class CardPositionMessage {
    CompressedCard compressedCard;
    CardPosition cardPosition;

    public CompressedCard getCompressedCard() {
        return compressedCard;
    }

    public CardPosition getCardPosition() {
        return cardPosition;
    }
}
