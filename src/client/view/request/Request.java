package client.view.request;

import client.Client;
import client.models.menus.*;
import client.view.View;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private String command;

    public void getNewCommand() {
        this.command = scanner.nextLine().toLowerCase().trim();
    }

    public void handleRequest(Client client, String serverName) throws InputException, ExitCommand {
        if (client.getCurrentMenu().equals(AccountMenu.getInstance())) {
            accountMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(BattleMenu.getInstance())) {
            battleRequestHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(CollectionMenu.getInstance())) {
            collectionMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(CustomGameMenu.getInstance())) {
            customGameMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(MainMenu.getInstance())) {
            mainMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(MultiPlayerMenu.getInstance())) {
            multiPlayerHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(Shop.getInstance())) {
            shopHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(SinglePlayerMenu.getInstance())) {
            singlePlayerMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().equals(StoryMenu.getInstance())) {
            storyMenuHandleRequest(client, serverName);
        }
    }

    private void storyMenuHandleRequest(Client client, String serverName) {

    }

    private void singlePlayerMenuHandleRequest(Client client, String serverName) throws InputException {
        SinglePlayerMenu singlePlayerMenu = SinglePlayerMenu.getInstance();
        if (RequestType.STORY.setMatcher(command).find()) {
            singlePlayerMenu.moveToStoryMenu(client);

        } else if (RequestType.CUSTOM_GAME.setMatcher(command).find()) {
            singlePlayerMenu.moveToCustomGameMenu(client);

        } else if (RequestType.HELP.setMatcher(command).find()) {
            singlePlayerMenu.showHelp();

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            singlePlayerMenu.exit(client);

        } else {
            throw new InputException("invalid command");
        }
    }

    private void shopHandleRequest(Client client, String serverName) throws InputException {
        Shop shop = Shop.getInstance();
        if (RequestType.EXIT.setMatcher(command).find()) {
            shop.exit(client);

        } else if (RequestType.SHOW_COLLECTION.setMatcher(command).find()) {
            shop.showCollection(client);

        } else if (RequestType.SEARCH.setMatcher(command).find()) {
            shop.searchInShop(
                    RequestType.SEARCH.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.SEARCH_COLLECTION.setMatcher(command).find()) {
            shop.searchInCollection(
                    RequestType.SEARCH_COLLECTION.getMatcher().group(1),
                    client
            );

        } else if (RequestType.BUY.setMatcher(command).find()) {
            shop.buy(
                    RequestType.BUY.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.SELL.setMatcher(command).find()) {
            shop.sell(
                    RequestType.SELL.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.SHOW.setMatcher(command).find()) {
            shop.showMarketCardsAndItems();

        } else if (RequestType.HELP.setMatcher(command).find()) {
            shop.showHelp();
        }
    }

    private void multiPlayerHandleRequest(Client client, String serverName) throws InputException {
        MultiPlayerMenu multiPlayerMenu = MultiPlayerMenu.getInstance();

        if (RequestType.SELECT_USER.setMatcher(command).find()) {
            multiPlayerMenu.selectUser(RequestType.SELECT_USER.getMatcher().group(1));

        } else if (RequestType.START_MULTIPLAYER_GAME.setMatcher(command).find()) {
            Matcher matcher = RequestType.START_MULTIPLAYER_GAME.getMatcher();
            multiPlayerMenu.startGame(matcher.group(1), Integer.parseInt(matcher.group(2)));

        } else {
            throw new InputException("invalid command");
        }
    }

    private void customGameMenuHandleRequest(Client client, String serverName) throws InputException {
        if (RequestType.START_GAME.setMatcher(command).find()) {
            Matcher matcher = RequestType.START_GAME.getMatcher();
            CustomGameMenu.getInstance().startGame(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)));

        } else {
            throw new InputException("invalid command");
        }
    }

    private void collectionMenuHandleRequest(Client client, String serverName) throws InputException {
        CollectionMenu collectionMenu = CollectionMenu.getInstance();
        if (RequestType.EXIT.setMatcher(command).find()) {
            collectionMenu.exit(client);

        } else if (RequestType.SEARCH.setMatcher(command).find()) {
            collectionMenu.search(RequestType.SEARCH.getMatcher().group(1));

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
            collectionMenu.showAllDecks(client);

        } else if (RequestType.SHOW_DECK.setMatcher(command).find()) {
            collectionMenu.showDeck(RequestType.SHOW_DECK.getMatcher().group(1));

        } else if (RequestType.SAVE.setMatcher(command).find()) {
            collectionMenu.save();
        } else {
            throw new InputException("invalid command");
        }
    }

    private void mainMenuHandleRequest(Client client, String serverName) throws ExitCommand, InputException {
        MainMenu mainMenu = MainMenu.getInstance();
        if (RequestType.ENTER_MENU.setMatcher(command).find()) {
            mainMenu.moveToMenu(client, serverName, RequestType.ENTER_MENU.getMatcher().group(1));
        } else if (RequestType.HELP.setMatcher(command).find()) {
            mainMenu.showHelp();

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            throw new ExitCommand();

        } else if (RequestType.SAVE.setMatcher(command).find()) {
            mainMenu.save(client, serverName);

        } else if (RequestType.LOGOUT.setMatcher(command).find()) {
            mainMenu.logout(client, serverName);

        } else {
            throw new InputException("invalid command");
        }
    }

    private void battleRequestHandleRequest(Client client, String serverName) throws InputException {
        BattleMenu battleMenu = BattleMenu.getInstance();

        if (RequestType.SINGLE_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToSinglePlayerMenu(client);

        } else if (RequestType.MULTI_PLAYER.setMatcher(command).find()) {
            battleMenu.moveToMultiPlayerMenu(client, serverName);

        } else if (RequestType.HELP.setMatcher(command).find()) {
            battleMenu.showHelp();

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            battleMenu.exit(client);

        } else {
            throw new InputException("invalid command");
        }
    }

    private void accountMenuHandleRequest(Client client, String serverName) throws InputException {
        AccountMenu accountMenu = AccountMenu.getInstance();
        if (RequestType.CREATE_ACCOUNT.setMatcher(command).find()) {
            String userName = RequestType.CREATE_ACCOUNT.getMatcher().group(1);

            View.getInstance().showGetPasswordMessage();
            this.getNewCommand();
            accountMenu.register(client, serverName, userName, command);

        } else if (RequestType.LOGIN.setMatcher(command).find()) {
            String userName = RequestType.LOGIN.getMatcher().group(1);

            View.getInstance().showGetPasswordMessage();
            this.getNewCommand();
            accountMenu.login(client, serverName, userName, command);

        } else if (RequestType.SHOW_LEADER_BOARD.setMatcher(command).find()) {
            accountMenu.showLeaderBoard(client, serverName);

        } else if (RequestType.HELP.setMatcher(command).find()) {
            accountMenu.showHelp();

        } else {
            throw new InputException("invalid command");
        }
    }
}
