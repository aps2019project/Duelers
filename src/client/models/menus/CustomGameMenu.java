package client.models.menus;

import client.Client;
import client.models.game.GameType;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class CustomGameMenu implements Menu {
    private static final CustomGameMenu CUSTOM_GAME_MENU = new CustomGameMenu();

    private CustomGameMenu() {
    }

    public static CustomGameMenu getInstance() {
        return CUSTOM_GAME_MENU;
    }

    public void startGame(String deckName, int mode, int numberOfFlags, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeNewCustomGameMessage(
                        client.getClientName(), serverName, GameType.values()[mode - 1], numberOfFlags, deckName, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(SinglePlayerMenu.getInstance());
    }

    @Override
    public void showHelp() {
        String help = "Custom Game:\n" +
                "\"start multiplayer game [mode] [number of flags(if needed)]\"";
        View.getInstance().showHelp(help);
    }


}
