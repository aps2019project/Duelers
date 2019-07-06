package server.chatCenter;

import server.Server;
import server.clientPortal.models.message.Message;
import server.dataCenter.DataCenter;
import server.exceptions.ClientException;

public class ChatCenter {
    private static ChatCenter ourInstance = new ChatCenter();

    public static ChatCenter getInstance() {
        return ourInstance;
    }

    private ChatCenter() {
    }

    public void getMessage(Message message) throws ClientException {

    }

    private void sendMessage(String senderUsername, String receiverUsername, String text) throws ClientException {
        String receiverClientName = DataCenter.getInstance().getClientName(receiverUsername);
        if (receiverClientName == null) {
            throw new ClientException("Chat Receiver Not Found!");
        }
        Message message = Message.makeChatMessage(Server.getInstance().serverName, receiverClientName,
                senderUsername, receiverUsername, text, 0);
        Server.getInstance().addToSendingMessages(message);
    }
}
