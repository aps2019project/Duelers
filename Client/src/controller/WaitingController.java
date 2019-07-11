package controller;

import models.message.Message;

import static models.Constants.SERVER_NAME;

public class WaitingController {
    private static WaitingController controller;

    public static WaitingController getInstance() {
        if (controller == null) {
            controller = new WaitingController();
        }
        return controller;
    }

    private WaitingController() {
    }


    public void cancel() {
        Client.getInstance().addToSendingMessagesAndSend(Message.makeCancelRequestMessage(SERVER_NAME));
    }
}
