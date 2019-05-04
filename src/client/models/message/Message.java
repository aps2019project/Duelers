package client.models.message;

import client.models.JsonConverter;
import client.models.map.Position;

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

    //SENDER:CLIENT
    private GetDataMessage getDataMessage;
    private OtherFields otherFields;
    private AccountFields accountFields;


    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public static Message makeGetDataMessage(String sender, String receiver, DataName dataName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.getDataMessage = new GetDataMessage(dataName);
        message.messageType = MessageType.GET_DATA;
        return message;
    }

    public static Message makeCreateDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.CREATE_DECK;
        return message;
    }

    public static Message makeRemoveDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.REMOVE_DECK;
        return message;
    }

    public static Message makeSelectDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.messageType = MessageType.SELECT_DECK;
        return message;
    }

    public static Message makeAddCardToDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.otherFields.setMyCardId(cardId);
        message.messageType = MessageType.ADD_TO_DECK;
        return message;
    }

    public static Message makeRemoveCardFromDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setDeckName(deckName);
        message.otherFields.setMyCardId(cardId);
        message.messageType = MessageType.REMOVE_FROM_DECK;
        return message;
    }

    public static Message makeBuyCardMessage(String sender, String receiver, String cardName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setCardName(cardName);
        message.messageType = MessageType.BUY_CARD;
        return message;
    }

    public static Message makeSellCardMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
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
        message.otherFields=new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.MOVE_TROOP;
        return message;
    }


    public static Message makeInsertMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.INSERT;
        return message;
    }

    public static Message makeAttackMessage(String sender, String receiver, String myCardId, String opponentCardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setMyCardId(myCardId);
        message.otherFields.setOpponentCardId(opponentCardId);
        message.messageType = MessageType.ATTACK;
        return message;
    }

    public static Message makeComboAttackMessage(String sender, String receiver , String opponentCardId ,String[] myCardIds,int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setOpponentCardId(opponentCardId);
        message.otherFields.setMyCardIds(myCardIds);
        message.messageType = MessageType.COMBO;
        return message;
    }

    public static Message makeUseSpecialPowerMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.otherFields=new OtherFields();
        message.otherFields.setMyCardId(cardId);
        message.otherFields.setPosition(position);
        message.messageType = MessageType.USE_SPECIAL_POWER;
        return message;
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
}
