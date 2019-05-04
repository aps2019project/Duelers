package client.models.message;

import client.models.JsonConverter;
import server.models.message.ChangeDeckMessage;

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
    private ChangeDeckMessage changeDeckMessage;


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
        message.changeDeckMessage = new ChangeDeckMessage(deckName);
        message.messageType = MessageType.CREATE_DECK;
        return message;
    }

    public static Message makeRemoveDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.changeDeckMessage = new ChangeDeckMessage(deckName);
        message.messageType = MessageType.REMOVE_DECK;
        return message;
    }

    public static Message makeSelectDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.changeDeckMessage = new ChangeDeckMessage(deckName);
        message.messageType = MessageType.SELECT_DECK;
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
