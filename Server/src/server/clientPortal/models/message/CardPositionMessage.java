package server.clientPortal.models.message;

import server.detaCenter.models.card.Card;
import server.clientPortal.models.comperessedData.CompressedCard;

public class CardPositionMessage {
    CompressedCard compressedCard;
    CardPosition cardPosition;

    public CardPositionMessage(Card card, CardPosition cardPosition) {
        this.compressedCard = card.toCompressedCard();
        this.cardPosition = cardPosition;
    }
}
