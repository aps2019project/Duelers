package client.models.message;

import client.models.account.AccountInfo;
import client.models.account.Collection;
import client.models.card.DeckInfo;
import client.models.game.GameType;
import client.models.map.Position;
import com.google.gson.Gson;
import client.models.account.TempAccount;
import client.models.game.Game;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;

    private Game game;
    private Collection shopCards;
    private TempAccount account;
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private DeckInfo[] stories;
    private Position[] positions;
    private String exceptionString;
    private String cardId;
    private String[] cardIds;
    private String spellId;
    private int turnNum;
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
        return new Gson().fromJson(messageJson, Message.class);
    }

    public static Message makePositionsCopyMessage(String sender, String receiver, Position[] positions, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.positions = positions;
        message.messageType = MessageType.POSITIONS_COPY;
        return message;
    }

    public static Message makeTroopPositionMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.position = position;
        message.messageType = MessageType.MOVE_TROOP;
        return message;
    }

    public static Message makeGetOriginalCardsMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.GET_ORIGINAL_CARDS;
        return message;
    }

    public static Message makeGetStoriesInfoMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.GET_STORIES;
        return message;
    }

    public static Message makeGetLeaderBoardMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.GET_LEADERBOARD;
        return message;
    }

    public static Message makeSaveAccountMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.SAVE_CHANGES;
        return message;
    }

    public static Message makeCreateDeckMessage(String sender, String receiver, String deckName, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.username = userName;
        message.messageType = MessageType.CREATE_DECK;
        return message;
    }

    public static Message makeRemoveDeckMessage(String sender, String receiver, String deckName, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.username = userName;
        message.messageType = MessageType.REMOVE_DECK;
        return message;
    }

    public static Message makeAddCardToDeckMessage(String sender, String receiver, String deckName, String cardId, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.username = userName;
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.ADD_TO_DECK;
        return message;
    }

    public static Message makeRemoveCardFromDeckMessage(String sender, String receiver, String deckName, String cardId, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.username = userName;
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.REMOVE_FROM_DECK;
        return message;
    }

    public static Message makeSelectDeckMessage(String sender, String receiver, String deckName, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.username = userName;
        message.messageType = MessageType.SELECT_DECK;
        return message;
    }

    public static Message makeBuyCardMessage(String sender, String receiver, String cardName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardName = cardName;
        message.messageType = MessageType.BUY_CARD;
        return message;
    }

    public static Message makeSellCardMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardId = cardId;
        message.messageType = MessageType.SELL_CARD;
        return message;
    }

    public static Message makeEndTurnMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.END_TURN;
        return message;
    }

    public static Message makeInsertMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.position = position;
        message.messageType = MessageType.INSERT;
        return message;
    }

    public static Message makeAttackMessage(String sender, String receiver, String myCardId, String opponentCardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[2];
        message.cardIds[0] = myCardId;
        message.cardIds[1] = opponentCardId;
        message.messageType = MessageType.ATTACK;
        return message;
    }

    public static Message makeComboAttackMessage(String sender, String receiver, int messageId, String... opponentAndMyCardIds) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = opponentAndMyCardIds;
        message.messageType = MessageType.COMBO;
        return message;
    }

    public static Message makeUseSpellMessage(String sender, String receiver, String cardId, String spellId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.spellId = spellId;
        message.position = position;
        message.messageType = MessageType.USE_SPELL;
        return message;
    }

    public static Message makeNewMultiPlayerGameMessage(String sender, String receiver, GameType gameType, int numberOfFlags, String opponentAccount, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.opponentUserName = opponentAccount;
        message.numberOfFlags = numberOfFlags;
        message.gameType = gameType;
        message.messageType = MessageType.NEW_2_GAME;
        return message;
    }

    public static Message makeNewCustomGameMessage(String sender, String receiver, GameType gameType, int numberOfFlags, String customDeck, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = customDeck;
        message.numberOfFlags = numberOfFlags;
        message.gameType = gameType;
        message.messageType = MessageType.NEW_DECK_GAME;
        return message;
    }

    public static Message makeNewStoryGameMessage(String sender, String receiver, int stage, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.stage = stage;
        message.messageType = MessageType.NEW_STORY_GAME;
        return message;
    }

    public static Message makeRegisterMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.username = userName;
        message.password = passWord;
        message.messageType = MessageType.REGISTER;
        return message;
    }

    public static Message makeLogInMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.username = userName;
        message.password = passWord;
        message.messageType = MessageType.LOG_IN;
        return message;
    }

    public static Message makeLogOutMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.LOG_OUT;
        return message;
    }

    public static Message makeSelectUserMessage(String sender, String receiver, String userName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.SELECT_USER;
        message.username = userName;
        return message;
    }

    public static Message makeSudoMessage(String sender, String receiver, String sudoCommand, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.SUDO;
        message.sudoCommand = sudoCommand;
        return message;
    }

    public String toJson() {
        return new Gson().toJson(this);
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

    public Game getGame() {
        return game;
    }

    public Collection getShopCards() {
        return shopCards;
    }

    public TempAccount getAccount() {
        return account;
    }

    public DeckInfo[] getCustomDecks() {
        return customDecks;
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }

    public DeckInfo[] getStories() {
        return stories;
    }

    public Position[] getPositions() {
        return positions;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public String getCardId() {
        return cardId;
    }

    public String[] getCardIds() {
        return cardIds;
    }

    public String getSpellId() {
        return spellId;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public String getOpponentUserName() {
        return opponentUserName;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String getCardName() {
        return cardName;
    }

    public int getNewValue() {
        return newValue;
    }

    public int getStage() {
        return stage;
    }

    public Position getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getDeckName() {
        return deckName;
    }

}
