package server.models.message;

import server.models.card.Card;
import server.models.comperessedData.CompressedCard;

public class CardPositionMessage {
    CompressedCard compressedCard;
    CardPosition cardPosition;

    public CardPositionMessage(Card card, CardPosition cardPosition) {
        this.compressedCard = card.toCompressedCard();
        this.cardPosition = cardPosition;
    }
}
