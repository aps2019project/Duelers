package client.models.menus;

import client.Client;
import client.models.message.Message;

public abstract class Menu {
    public void sendSudoCommand(Client client, String serverName, String sudoCommand) {
        client.addToSendingMessages(
                Message.makeSudoMessage(
                        client.getClientName(), serverName, sudoCommand, 0
                )
        );
        client.sendMessages();
    }

    public abstract void showHelp();

    public abstract void exit(Client client);
}