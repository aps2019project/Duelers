package controller;

import models.game.GameType;
import models.message.Message;

public class CustomGameMenuController {

    private static CustomGameMenuController ourInstance;
    private String help;

    private CustomGameMenuController() {
    }

    public static CustomGameMenuController getInstance() {
        if (ourInstance == null) {
            ourInstance = new CustomGameMenuController();
        }
        return ourInstance;
    }

    public void startGame(String deckName, int mode, int numberOfFlags, Client client, String serverName){
        client.addToSendingMessagesAndSend(
                Message.makeNewCustomGameMessage(
                        client.getClientName(), serverName, GameType.values()[mode - 1], numberOfFlags, deckName, 0
                )
        );
    }
}
