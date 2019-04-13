package models.Message;

public abstract class Message {
    private String sender;
    private String receiver;

    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
