package server.chatCenter;

public class ChatCenter {
    private static ChatCenter ourInstance = new ChatCenter();

    public static ChatCenter getInstance() {
        return ourInstance;
    }

    private ChatCenter() {
    }
}
