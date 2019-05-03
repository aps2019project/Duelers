package client.models.message;

import client.models.JsonConverter;

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


    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public String toJson() {
        return JsonConverter.toJson(this);
    }

    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
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
