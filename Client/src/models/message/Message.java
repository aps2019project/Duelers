package models.message;

import client.models.JsonConverter;
import client.models.game.GameType;
import client.models.map.Position;
import controller.Client;
import models.game.map.Position;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;

    //SENDER:SERVER
    private GameCopyMessage gameCopyMessage;
    private OriginalCardsCopyMessage originalCardsCopyMessage;
    private AccountCopyMessage accountCopyMessage;
    private LeaderBoardCopyMessage leaderBoardCopyMessage;
    private StoriesCopyMessage storiesCopyMessage;
    private CardPositionMessage cardPositionMessage;
    private TroopUpdateMessage troopUpdateMessage;
    private GameUpdateMessage gameUpdateMessage;
    private ExceptionMessage exceptionMessage;
    private OpponentInfoMessage opponentInfoMessage;
    private GameFinishMessage gameFinishMessage;

    //SENDER:CLIENT
    private GetDataMessage getDataMessage;
    private OtherFields otherFields;
    private AccountFields accountFields;
    private NewGameFields newGameFields;


    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public static Message makeGetDataMessage(String sender, String receiver, DataName dataName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.getDataMessage = new GetDataMessage(dataName);
        message.messageType = MessageType.GET_DATA;
        return message;
    }

    public static Message makeRegisterMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.accountFields = new AccountFields(userName, passWord);
        message.messageType = MessageType.REGISTER;
        return message;
    }

    public static Message makeLogInMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.accountFields = new AccountFields(userName, passWord);
        message.messageType = MessageType.LOG_IN;
        return message;
    }

    public static Message makeLogOutMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.LOG_OUT;
        return message;
    }

    public static Message makeCreateDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.CREATE_DECK;
        return message;
    }

    public static Message makeRemoveDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.REMOVE_DECK;
        return message;
    }

    public static Message makeSelectDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.SELECT_DECK;
        return message;
    }

    public static Message makeAddCardToDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.otherFields.setMyCardId(cardId);
        message.messageType = MessageType.ADD_TO_DECK;
        return message;
    }

    public static Message makeRemoveCardFromDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.otherFields.setMyCardId(cardId);
        message.messageType = MessageType.REMOVE_FROM_DECK;
        return message;
    }

    public static Message makeBuyCardMessage(String sender, String receiver, String cardName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setCardName(cardName);
        message.messageType = MessageType.BUY_CARD;
        return message;
    }

    public static Message makeSellCardMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.messageType = MessageType.SELL_CARD;
        return message;
    }

    public static Message makeEndTurnMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.END_TURN;
        return message;
    }

    public static Message makeMoveTroopMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.MOVE_TROOP;
        return message;
    }

    public static Message makeInsertMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.INSERT;
        return message;
    }

    public static Message makeAttackMessage(String sender, String receiver, String myCardId, String opponentCardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setMyCardId(myCardId);
        message.otherFields.setOpponentCardId(opponentCardId);
        message.messageType = MessageType.ATTACK;
        return message;
    }

    public static Message makeComboAttackMessage(String sender, String receiver, String opponentCardId, String[] myCardIds, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setOpponentCardId(opponentCardId);
        message.otherFields.setMyCardIds(myCardIds);
        message.messageType = MessageType.COMBO;
        return message;
    }

    public static Message makeUseSpecialPowerMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.USE_SPECIAL_POWER;
        return message;
    }

    public static Message makeNewMultiPlayerGameMessage(String sender, String receiver, GameType gameType, int numberOfFlags, String opponentUsername, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.newGameFields = new NewGameFields();
        message.newGameFields.setOpponentUsername(opponentUsername);
        message.newGameFields.setNumberOfFlags(numberOfFlags);
        message.newGameFields.setGameType(gameType);
        message.messageType = MessageType.NEW_MULTIPLAYER_GAME;
        return message;
    }

    public static Message makeNewCustomGameMessage(String sender, String receiver, GameType gameType, int numberOfFlags, String customDeckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.newGameFields = new NewGameFields();
        message.newGameFields.setCustomDeckName(customDeckName);
        message.newGameFields.setNumberOfFlags(numberOfFlags);
        message.newGameFields.setGameType(gameType);
        message.messageType = MessageType.NEW_DECK_GAME;
        return message;
    }

    public static Message makeNewStoryGameMessage(String sender, String receiver, int stage, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.newGameFields = new NewGameFields();
        message.newGameFields.setStage(stage);
        message.messageType = MessageType.NEW_STORY_GAME;
        return message;
    }

    public static Message makeSelectUserMessage(String sender, String receiver, String opponentUserName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.newGameFields = new NewGameFields();
        message.newGameFields.setOpponentUsername(opponentUserName);
        message.messageType = MessageType.SELECT_USER;
        return message;
    }

    public static Message makeSudoMessage(String sender, String receiver, String sudoCommand, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields = new OtherFields();
        message.otherFields.setSudoCommand(sudoCommand);
        message.messageType = MessageType.SUDO;
        return message;
    }

    public String toJson() {
        return Client.clientPortal.models.JsonConverter.toJson(this);
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

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public GameCopyMessage getGameCopyMessage() {
        return gameCopyMessage;
    }

    public OriginalCardsCopyMessage getOriginalCardsCopyMessage() {
        return originalCardsCopyMessage;
    }

    public AccountCopyMessage getAccountCopyMessage() {
        return accountCopyMessage;
    }

    public LeaderBoardCopyMessage getLeaderBoardCopyMessage() {
        return leaderBoardCopyMessage;
    }

    public StoriesCopyMessage getStoriesCopyMessage() {
        return storiesCopyMessage;
    }

    public CardPositionMessage getCardPositionMessage() {
        return cardPositionMessage;
    }

    public TroopUpdateMessage getTroopUpdateMessage() {
        return troopUpdateMessage;
    }

    public GameUpdateMessage getGameUpdateMessage() {
        return gameUpdateMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

    public OpponentInfoMessage getOpponentInfoMessage() {
        return opponentInfoMessage;
    }

    public GameFinishMessage getGameFinishMessage() {
        return gameFinishMessage;
    }
}
