package client.view.request;

import client.Client;
import client.models.menus.*;

import java.util.Scanner;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private String command;
    private RequestType requestType = null;

    public RequestType getRequestType() {
        return requestType;
    }

    public void getNewCommand() {
        this.command = scanner.nextLine();
    }

    public void handleRequest(Client client, String serverName) throws InputException {
        if (client.getCurrentMenu().equals(AccountMenu.getInstance())) {
            accountMenuHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(BattleMenu.getInstance())) {
            battleRequestHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(CollectionMenu.getInstance())) {

        } else if (client.getCurrentMenu().equals(CustomGameMenu.getInstance())) {

        } else if (client.getCurrentMenu().equals(MainMenu.getInstance())) {
            mainMenuHandleRequest(client , serverName);
        } else if (client.getCurrentMenu().equals(MultiPlayerMenu.getInstance())) {

        } else if (client.getCurrentMenu().equals(Shop.getInstance())) {

        } else if (client.getCurrentMenu().equals(SinglePlayerMenu.getInstance())) {

        }
        return false;
    }

    private void mainMenuHandleRequest(Client client, String serverName) {
        MainMenu mainMenu = MainMenu.getInstance();
        if (RequestType.ENTER_MENU.setMatcher(command).find()){
            mainMenu.moveToMenu(client,RequestType.ENTER_MENU.getMatcher().group(1));
        }
    }

    private void battleRequestHandleRequest(Client client, String serverName) {
        BattleMenu battleMenu = BattleMenu.getInstance();
        if (RequestType.SINGLE_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToSinglePlayerMenu(client);
        } else if (RequestType.MULTI_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToMultiPlayerMenu(client, serverName);
        }
    }

    private void accountMenuHandleRequest(Client client, String serverName) throws InputException {
        AccountMenu accountMenu = AccountMenu.getInstance();
        if (RequestType.CREATE_ACCOUNT.setMatcher(command).find()) {
            String userName = RequestType.CREATE_ACCOUNT.getMatcher().group(1);
            this.getNewCommand();
            accountMenu.register(client, serverName, userName, command);
        } else if (RequestType.LOGIN.setMatcher(command).find()) {
            String userName = RequestType.LOGIN.getMatcher().group(1);
            this.getNewCommand();
            accountMenu.login(client, serverName, userName, command);
        } else if (RequestType.SHOW_LEADER_BOARD.setMatcher(command).find()) {
            accountMenu.showLeaderBoard(client, serverName);
        } else if (RequestType.HELP.setMatcher(command).find()) {
            accountMenu.help();
        } else
            throw new InputException("invalid command");
    }
}
