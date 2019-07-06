package server.clientPortal.models.message;

public class ChatMessage {
    private String messageSender,messageReceiver,textMessage;

    public ChatMessage(String messageSender, String messageReceiver, String textMessage) {
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        this.textMessage = textMessage;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public String getMessageReceiver() {
        return messageReceiver;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
