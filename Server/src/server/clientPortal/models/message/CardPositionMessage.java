package server.clientPortal.models.message;

import server.detaCenter.models.card.Card;
import server.clientPortal.models.comperessedData.CompressedCard;

class CardPositionMessage {
    private CompressedCard compressedCard;
    private CardPosition cardPosition;

    CardPositionMessage(Card card, CardPosition cardPosition) {
        this.compressedCard = card.toCompressedCard();
        this.cardPosition = cardPosition;
    }
}
