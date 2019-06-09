package models.message;

import models.game.map.Position;

public class OtherFields {
    private String deckName;
    private String cardName;
    private String myCardId;
    private String opponentCardId;
    private String[] myCardIds;
    private Position position;
    private String sudoCommand;

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setMyCardId(String myCardId) {
        this.myCardId = myCardId;
    }

    public void setOpponentCardId(String opponentCardId) {
        this.opponentCardId = opponentCardId;
    }

    public void setMyCardIds(String[] myCardIds) {
        this.myCardIds = myCardIds;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSudoCommand(String sudoCommand) {
        this.sudoCommand = sudoCommand;
    }
}
