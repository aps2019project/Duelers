package server.models.message;

import server.models.account.Collection;
import server.models.game.Game;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;

    private GameCopyMessage gameCopyMessage;
    private OriginalCardsCopyMessage originalCardsCopyMessage;

    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message makeGameCopyMessage(String sender, String receiver, Game game, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.gameCopyMessage = new GameCopyMessage(game);
        message.messageType = MessageType.GAME_COPY;
        return message;
    }

    public static Message makeOriginalCardsCopyMessage(String sender, String receiver, Collection originalCards, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.originalCardsCopyMessage = new OriginalCardsCopyMessage(originalCards);
        message.messageType = MessageType.ORIGINAL_CARDS_COPY;
        return message;
    }
}
