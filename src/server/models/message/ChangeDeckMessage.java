package server.models.message;

public class ChangeDeckMessage {
    private String deckName;
    private String cardId;

    public ChangeDeckMessage(String deckName) {
        this.deckName = deckName;
    }

    public ChangeDeckMessage(String deckName, String cardId) {
        this.deckName = deckName;
        this.cardId = cardId;
    }
}
