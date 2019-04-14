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
    private String userName,passWord;

    private Message(String sender, String receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String sender, String receiver, Game game) {
        this.sender = sender;
        this.receiver = receiver;
        this.game = game;
        messageType=MessageType.GAME_COPY;
    }

    public Message(String sender, String receiver, Card[] shopCards) {
        this.sender = sender;
        this.receiver = receiver;
        this.shopCards = shopCards;
        messageType=MessageType.SHOP_COPY;
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
        messageType=MessageType.CUSTOMDECKS_COPY;
    }

    public Message(String sender, String receiver, Account[] leaderBoard) {
        this.sender = sender;
        this.receiver = receiver;
        this.leaderBoard = leaderBoard;
        messageType=MessageType.LEADERBOARD_COPY;
    }

    public Message(String sender, String receiver, Story[] stories) {
        this.sender = sender;
        this.receiver = receiver;
        this.stories = stories;
        messageType=MessageType.STORIES_COPY;
    }

    public Message(String sender, String receiver, Position[] positions) {
        this.sender = sender;
        this.receiver = receiver;
        this.positions = positions;
        messageType=MessageType.POSITIONS_COPY;
    }

    public Message(String sender, String receiver, String userName, String passWord) {
        this.sender = sender;
        this.receiver = receiver;
        this.userName = userName;
        this.passWord = passWord;
        messageType=MessageType.LOG_IN;
    }

    //....................

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
