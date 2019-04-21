package client.models.menus;

import client.Client;
import client.models.account.Account;
import client.models.game.GameType;
import client.models.message.Message;
import client.view.request.InputException;

public class MultiPlayerMenu implements Menu {
    private static final MultiPlayerMenu MULTI_PLAYER_MENU = new MultiPlayerMenu();
    private Account secondAccount;

    private MultiPlayerMenu() {
    }

    public static MultiPlayerMenu getInstance() {
        return MULTI_PLAYER_MENU;
    }

    public void setSecondAccount(Account secondAccount) {
        this.secondAccount = secondAccount;
    }

    public void selectUser(String userName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeSelectUserMessage(
                        client.getClientName(), serverName, userName, 0
                )
        );
        client.sendMessages();

        if (client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void startGame(int mode, int numberOfFlags, Client client, String serverName) throws InputException {
        if (secondAccount == null) {
            throw new InputException("opponent is not selected");
        }

        client.addToSendingMessages(
                Message.makeNewMultiPlayerGameMessage(
                        client.getClientName(), serverName, GameType.values()[mode - 1],
                        numberOfFlags, secondAccount.getUserName(), 0
                )
        );

        if (client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        client.setCurrentMenu(GameCommands.getInstance());
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(BattleMenu.getInstance());
    }
}
