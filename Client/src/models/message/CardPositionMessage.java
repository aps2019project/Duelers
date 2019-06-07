package models.message;

import models.comperessedData.CompressedCard;

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
