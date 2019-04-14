package models.message;

import models.account.Account;
import models.card.Card;
import models.card.Deck;
import models.game.Game;
import models.game.Story;
import models.map.Position;

public abstract class Message {
    private MessageType messageType;
    private String sender;
    private String receiver;
    private Game game;
    private Card[] shopCards;
    private Account account;
    private Deck[] customDecks;
    private Account[] leaderBoard;
    private Story[] stories;
    private Position[] positions;
    private String cardId1,cardId2;
    private int changeValue;
    private Position position;

    private Message(){

    }

    public Message(String sender, String receiver, Game game) {
        this.sender = sender;
        this.receiver = receiver;
        this.game = game;
        messageType=MessageType.GAME;
    }

    public Message(String sender, String receiver, Card[] shopCards) {
        this.sender = sender;
        this.receiver = receiver;
        this.shopCards = shopCards;
        messageType=MessageType.SHOP;
    }

    public Message(String sender, String receiver, Account account) {
        this.sender = sender;
        this.receiver = receiver;
        this.account = account;
    }

    public Message(String sender, String receiver, Deck[] customDecks) {
        this.sender = sender;
        this.receiver = receiver;
        this.customDecks = customDecks;
        messageType=MessageType.CUSTOMDECKS;
    }

    public Message(String sender, String receiver, Account[] leaderBoard) {
        this.sender = sender;
        this.receiver = receiver;
        this.leaderBoard = leaderBoard;
        messageType=MessageType.LEADERBOARD;
    }

    public Message(String sender, String receiver, Story[] stories) {
        this.sender = sender;
        this.receiver = receiver;
        this.stories = stories;
        messageType=MessageType.STORIES;
    }

    public Message(String sender, String receiver, Position[] positions) {
        this.sender = sender;
        this.receiver = receiver;
        this.positions = positions;
        messageType=MessageType.POSITIONS;
    }

    //....................

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
