package client.view.request;

import client.Client;
import client.models.menus.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private String command;
    private RequestType requestType = null;

    public void getNewCommand() {
        this.command = scanner.nextLine();
    }

    public void handleRequest(Client client, String serverName) throws InputException, ExitCommand {
        if (client.getCurrentMenu().equals(AccountMenu.getInstance())) {
            accountMenuHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(BattleMenu.getInstance())) {
            battleRequestHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(CollectionMenu.getInstance())) {
            collectionMenuHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(CustomGameMenu.getInstance())) {

        } else if (client.getCurrentMenu().equals(MainMenu.getInstance())) {
            mainMenuHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().equals(MultiPlayerMenu.getInstance())) {

        } else if (client.getCurrentMenu().equals(Shop.getInstance())) {

        } else if (client.getCurrentMenu().equals(SinglePlayerMenu.getInstance())) {

        }
    }

    private void collectionMenuHandleRequest(Client client, String serverName) {
        CollectionMenu collectionMenu = CollectionMenu.getInstance();
        if (RequestType.EXIT.setMatcher(command).find()) {
            collectionMenu.backToMain(client);
        } else if (RequestType.HELP.setMatcher(command).find()) {
            collectionMenu.showHelp();
        } else if (RequestType.SHOW.setMatcher(command).find()) {
            collectionMenu.showItemsAndCards();
        } else if (RequestType.CREATE_DECK.setMatcher(command).find()) {
            collectionMenu.newDeck(RequestType.CREATE_DECK.getMatcher().group(1));
        } else if (RequestType.DELETE_DECK.setMatcher(command).find()) {
            collectionMenu.removeDeck(RequestType.DELETE_DECK.getMatcher().group(1));
        } else if (RequestType.ADD_TO_DECK.setMatcher(command).find()) {
            Matcher matcher = RequestType.ADD_TO_DECK.getMatcher();
            collectionMenu.addCardToDeck(matcher.group(2), matcher.group(1));
        } else if (RequestType.REMOVE_FROM_DECK.setMatcher(command).find()) {
            Matcher matcher = RequestType.ADD_TO_DECK.getMatcher();
            collectionMenu.removeCardFromDeck(matcher.group(2), matcher.group(1));
        } else if (RequestType.VALIDATE_DECK.setMatcher(command).find()) {
            collectionMenu.validateDeck(RequestType.VALIDATE_DECK.getMatcher().group(1));
        } else if (RequestType.SELECT_MAIN_DECK.setMatcher(command).find()) {
            collectionMenu.selectDeck(RequestType.SELECT_MAIN_DECK.getMatcher().group(2));
        } else if (RequestType.SHOW_ALL_DECKS.setMatcher(command).find()) {
            collectionMenu.showAllDecks();
        }else if (RequestType.SHOW_DECK.setMatcher(command).find()){
            collectionMenu.showDeck(RequestType.SHOW_DECK.getMatcher().group(1));
        }
    }

    private void mainMenuHandleRequest(Client client, String serverName) throws ExitCommand {
        MainMenu mainMenu = MainMenu.getInstance();
        if (RequestType.ENTER_MENU.setMatcher(command).find()) {
            mainMenu.moveToMenu(client, RequestType.ENTER_MENU.getMatcher().group(1));
        }
        if (RequestType.HELP.setMatcher(command).find()) {
            mainMenu.showHelp();
        }
        if (RequestType.EXIT.setMatcher(command).find())
            throw new ExitCommand();
        if (RequestType.SAVE.setMatcher(command).find()) {
            mainMenu.save();
        }
        if (RequestType.LOGOUT.setMatcher(command).find()) {
            mainMenu.logout();
        }
    }

    private void battleRequestHandleRequest(Client client, String serverName) {
        BattleMenu battleMenu = BattleMenu.getInstance();
        if (RequestType.SINGLE_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToSinglePlayerMenu(client);
        } else if (RequestType.MULTI_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToMultiPlayerMenu(client, serverName);
        } else if (RequestType.HELP.setMatcher(command).find()) {
            battleMenu.showHelp();
        } else if (RequestType.EXIT.setMatcher(command).find()) {
            battleMenu.backToMain(client);
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
