package client.models.menus;

import client.Client;
import client.models.account.AccountInfo;
import client.models.game.GameType;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class MultiPlayerMenu extends Menu {
    private static MultiPlayerMenu ourInstance;
    private AccountInfo secondAccount;

    private MultiPlayerMenu() {
    }

    public static MultiPlayerMenu getInstance() {
        if (ourInstance == null) {
            ourInstance = new MultiPlayerMenu();
        }
        return ourInstance;
    }

    public void setSecondAccount(AccountInfo secondAccount) {
        this.secondAccount = secondAccount;
    }

    public void selectUser(String userName, Client client, String serverName) throws InputException {
        if (client.getAccount().getUsername().equals(userName)) {
            throw new InputException("this is your username");
        }
        client.addToSendingMessages(
                Message.makeSelectUserMessage(
                        client.getClientName(), serverName, userName, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
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
                        numberOfFlags, secondAccount.getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }

        client.setCurrentMenu(GameCommands.getInstance());
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(BattleMenu.getInstance());
    }

    @Override
    public void showHelp() {
        String help = "MultiPlayer Menu:\n" +
                "game modes:\n" +
                "1. kill hero\n" +
                "2. one flag\n" +
                "3. multi flag\n" +
                "\"select user [username]\"\n" +
                "\"start multiplayer game [mode(number)] [number of flags(if needed)]\"";
        View.getInstance().showHelp(help);
    }
}
