package server.models.message;

public class CreateDeckMessage {
    private String deckName;

    public CreateDeckMessage(String deckName) {
        this.deckName = deckName;
    }
}
