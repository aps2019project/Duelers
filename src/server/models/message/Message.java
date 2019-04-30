package server.models.message;

import server.models.JsonConverter;
import server.models.account.Account;
import server.models.account.AccountInfo;
import server.models.account.Collection;
import server.models.account.TempAccount;
import server.models.card.Deck;
import server.models.card.spell.DeckInfo;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Story;
import server.models.map.Position;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;
    private AccountInfo accountInfo;
    private Game game;
    private Collection originalCards;
    private TempAccount account;
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private DeckInfo[] stories;
    private Position[] positions;
    private String exceptionString;
    private String cardId;
    private String[] cardIds;
    private int numberOfFlags;
    private String cardName;
    private int newValue;
    private int stage;
    private Position position;
    private String username, password;
    private String deckName;
    private String opponentUserName;
    private GameType gameType;
    private String sudoCommand;

    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public static Message makeGameCopyMessage(String sender, String receiver, Game game, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.game = game;
        message.messageType = MessageType.GAME_COPY;
        return message;
    }

    public static Message makeOriginalCardsCopyMessage(String sender, String receiver, Collection shopCards, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.originalCards = shopCards;
        message.messageType = MessageType.ORIGINAL_CARDS_COPY;
        return message;
    }

    public static Message makeAccountCopyMessage(String sender, String receiver, Account account, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.account = new TempAccount(account);
        message.messageType = MessageType.ACCOUNT_COPY;
        return message;
    }

    public static Message makeCustomDecksCopyMessage(String sender, String receiver, Deck[] customDecks, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.customDecks = new DeckInfo[customDecks.length];
        for (int i = 0; i < customDecks.length; i++) {
            message.customDecks[i] = new DeckInfo(customDecks[i]);
        }
        message.messageType = MessageType.CUSTOM_DECKS_COPY;
        return message;
    }

    public static Message makeLeaderBoardCopyMessage(String sender, String receiver, Account[] leaderBoard, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.leaderBoard = new AccountInfo[leaderBoard.length];
        for (int i = 0; i < leaderBoard.length; i++) {
            message.leaderBoard[i] = new AccountInfo(leaderBoard[i]);
        }
        message.messageType = MessageType.LEADERBOARD_COPY;
        return message;
    }

    public static Message makeStoriesCopyMessage(String sender, String receiver, Story[] stories, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.stories = new DeckInfo[stories.length];//TODO:reCode Story
        for (int i = 0; i < stories.length; i++) {
            message.stories[i] = new DeckInfo(stories[i]);
        }
        message.messageType = MessageType.STORIES_COPY;
        return message;
    }

    public static Message makePositionsCopyMessage(String sender, String receiver, Position[] positions, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.positions = positions;
        message.messageType = MessageType.POSITIONS_COPY;
        return message;
    }

    public static Message makeTroopPositionMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.position = position;
        message.messageType = MessageType.MOVE_TROOP;
        return message;
    }

    public static Message makeToHandMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.messageType = MessageType.TO_HAND;
        return message;
    }

    public static Message makeToNextMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.messageType = MessageType.TO_NEXT;
        return message;
    }

    public static Message makeToGraveYardMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.messageType = MessageType.TO_GRAVEYARD;
        return message;
    }

    public static Message makeToCollectedItemsMessage(String sender, String receiver, String cardId, String username, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.username = username;
        message.messageType = MessageType.TO_COLLECTED_CARDS;
        return message;
    }

    public static Message makeToMapMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.position = position;
        message.messageType = MessageType.TO_MAP;
        return message;
    }

    public static Message makeChangeAPMessage(String sender, String receiver, String cardId, int newValue, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.newValue = newValue;
        message.messageType = MessageType.TROOP_AP;
        return message;
    }

    public static Message makeChangeHPMessage(String sender, String receiver, String cardId, int newValue, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.newValue = newValue;
        message.messageType = MessageType.TROOP_HP;
        return message;
    }

    public static Message makeExceptionMessage(String sender, String receiver, String exceptionString, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.exceptionString = exceptionString;
        message.messageType = MessageType.SEND_EXCEPTION;
        return message;
    }

    public static Message makeEndTurnMessage(String sender, String receiver, int newValue, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.newValue = newValue;
        message.messageType = MessageType.END_TURN;
        return message;
    }

    public static Message makeAccountInfoMessage(String sender, String receiver, AccountInfo accountInfo, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.accountInfo = accountInfo;
        message.messageType = MessageType.ACCOUNT_INFO;
        return message;

    }

    public String toJson() {
        return JsonConverter.toJson(this);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getStage() {
        return stage;
    }

    public String getCardId() {
        return cardId;
    }

    public String[] getCardIds() {
        return cardIds;
    }

    public String getCardName() {
        return cardName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDeckName() {
        return deckName;
    }

    public String getOpponentUserName() {
        return opponentUserName;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String getSudoCommand() {
        return sudoCommand;
    }

    public Position[] getPositions() {
        return positions;
    }

    public Position getPosition() {
        return position;
    }
}
