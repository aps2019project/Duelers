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

        if (RequestType.SUDO.setMatcher(command).find()) {
            client.getCurrentMenu().sendSudoCommand(client, serverName, command);

        } else if (client.getCurrentMenu().getClass().getName().equals(AccountMenu.class.getName())) {
            accountMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(BattleMenu.class.getName())) {
            battleMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(CollectionMenu.class.getName())) {
            collectionMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(CustomGameMenu.class.getName())) {
            customGameMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(MainMenu.class.getName())) {
            mainMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(MultiPlayerMenu.class.getName())) {
            multiPlayerHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(Shop.class.getName())) {
            shopHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(SinglePlayerMenu.class.getName())) {
            singlePlayerMenuHandleRequest(client, serverName);

        } else if (client.getCurrentMenu().getClass().getName().equals(StoryMenu.class.getName())) {
            storyMenuHandleRequest(client, serverName);
        } else if (client.getCurrentMenu().getClass().getName().equals(GameCommands.class.getName())) {
            gameCommandsHandleRequest(client, serverName);

        }
    }

    private void gameCommandsHandleRequest(Client client, String serverName) throws InputException {
        GameCommands gameCommands = GameCommands.getInstance();
        if (GameRequestType.GAME_INFO.matches(command)) {
            gameCommands.showGameInfo();

        } else if (GameRequestType.SHOW_MY_MINIONS.matches(command)) {
            gameCommands.showMyMinions();

        } else if (GameRequestType.SHOW_OPP_MINIONS.matches(command)) {
            gameCommands.showOppMinions();

        } else if (GameRequestType.SHOW_CARD_INFO.matches(command)) {
            String cardId = GameRequestType.SHOW_CARD_INFO.getMatcher().group(1);
            gameCommands.showCardInfo(cardId);

        } else if (GameRequestType.SELECT_CARD.matches(command)) {
            String cardId = GameRequestType.SHOW_CARD_INFO.getMatcher().group(1);
            gameCommands.selectCard(cardId);

        } else if (GameRequestType.MOVE.matches(command)) {
            Matcher matcher = GameRequestType.MOVE.getMatcher();
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            gameCommands.move(client, serverName, row, column);

        } else if (GameRequestType.ATTACK.matches(command)) {
            String oppCardId = GameRequestType.ATTACK.getMatcher().group(1);
            gameCommands.attack(client, serverName, oppCardId);

        } else if (GameRequestType.ATTACK_COMBO.matches(command)) {
            Matcher matcher = GameRequestType.ATTACK_COMBO.getMatcher();
            String oppCardId = matcher.group(1);
            String[] cardIds = matcher.group(2).split(" ");
            gameCommands.attackCombo(client, serverName, oppCardId, cardIds);

        } else if (GameRequestType.USE_SPECIAL_POWER.matches(command)) {
            Matcher matcher = GameRequestType.USE_SPECIAL_POWER.getMatcher();
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            gameCommands.useSpecialPower(client, serverName, row, column);

        } else if (GameRequestType.SHOW_HAND.matches(command)) {
            gameCommands.showHand(client);

        } else if (GameRequestType.INSERT_CARD.matches(command)) {
            Matcher matcher = GameRequestType.INSERT_CARD.getMatcher();
            String cardId = matcher.group(1);
            int row = Integer.parseInt(matcher.group(2));
            int column = Integer.parseInt(matcher.group(3));
            gameCommands.insertCard(client, serverName, cardId, row, column);

        } else if (GameRequestType.END_TURN.matches(command)) {
            gameCommands.endTurn(client, serverName);

        } else if (GameRequestType.SHOW_COLLECTABLES.matches(command)) {
            gameCommands.showCollectibleItems();

        } else if (GameRequestType.SELECT_ITEM.matches(command)) {
            String itemID = GameRequestType.SELECT_ITEM.getMatcher().group(1);
            gameCommands.selectItem(itemID);

        } else if (GameRequestType.SHOW_INFO_OF_ITEM.matches(command)) {
            if (!gameCommands.isItemSelected()) {
                throw new InputException("select an item");
            }
            gameCommands.showSelectedItemInfo();

        } else if (GameRequestType.USE_ITEM.matches(command)) {
            if (!gameCommands.isItemSelected()) {
                throw new InputException("select an item");
            }
            Matcher matcher = GameRequestType.USE_ITEM.getMatcher();
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            gameCommands.useItem(client, serverName, row, column);

        } else if (GameRequestType.SHOW_NEXT_CARD.matches(command)) {
            gameCommands.showNextCard();

        } else if (GameRequestType.ENTER_GRAVE_YARD.matches(command)) {
            gameCommands.enterGraveYard();

        } else if (gameCommands.isInGraveYard() && GameRequestType.SHOW_INFO_OF_CARD_INGRAVEYARD.matches(command)) {
            Matcher matcher = GameRequestType.SHOW_CRADS_IN_GRAVE_YARD.getMatcher();
            String cardId = matcher.group(1);
            gameCommands.showCardInfoInGraveYard(cardId);

        } else if (gameCommands.isInGraveYard() && GameRequestType.SHOW_CRADS_IN_GRAVE_YARD.matches(command)) {
            gameCommands.showCardsInGraveYard();

        } else if (gameCommands.isInGraveYard() && GameRequestType.EXIT.matches(command)) {
            gameCommands.exitFromGraveYard();

        } else if (GameRequestType.HELP.matches(command)) {
            gameCommands.showGameActions();

        } else if (GameRequestType.END_GAME.matches(command)) {
            gameCommands.endGame();

        } else if (GameRequestType.SHOW_MENU_HELP.matches(command)) {
            gameCommands.showHelp();

        } else
            throw new InputException("invalid command");

    }

    private void storyMenuHandleRequest(Client client, String serverName) throws InputException {
        StoryMenu storyMenu = StoryMenu.getInstance();
        if (RequestType.START_GAME_IN_STORY_MENU.setMatcher(command).find()) {
            int stage = Integer.parseInt(RequestType.START_GAME_IN_STORY_MENU.getMatcher().group(1));
            storyMenu.startGame(stage, client, serverName);

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            storyMenu.exit(client);

        } else if (RequestType.HELP.setMatcher(command).find()) {
            storyMenu.showHelp();

        } else {
            throw new InputException("invalid command");
        }

    }

    private void singlePlayerMenuHandleRequest(Client client, String serverName) throws InputException {
        SinglePlayerMenu singlePlayerMenu = SinglePlayerMenu.getInstance();
        if (RequestType.STORY.setMatcher(command).find()) {
            singlePlayerMenu.moveToStoryMenu(client, serverName);

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

        } else if (RequestType.SEARCH_COLLECTION.setMatcher(command).find()) {
            shop.searchInCollection(
                    RequestType.SEARCH_COLLECTION.getMatcher().group(1),
                    client
            );

        } else if (RequestType.SEARCH.setMatcher(command).find()) {
            shop.searchInShop(
                    RequestType.SEARCH.getMatcher().group(1)
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
        } else {
            throw new InputException("invalid command");
        }
    }

    private void multiPlayerHandleRequest(Client client, String serverName) throws InputException {
        MultiPlayerMenu multiPlayerMenu = MultiPlayerMenu.getInstance();

        if (RequestType.SELECT_USER.setMatcher(command).find()) {
            multiPlayerMenu.selectUser(
                    RequestType.SELECT_USER.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.START_MULTIPLAYER_GAME.setMatcher(command).find()) {
            Matcher matcher = RequestType.START_MULTIPLAYER_GAME.getMatcher();
            /*if (matcher.group(3) == null) {
                customGameMenu.startGame(
                        matcher.group(1), Integer.parseInt(matcher.group(2)), 0,
                        client, serverName
                );
            } else {
                customGameMenu.startGame(
                        matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                        client, serverName
                );
            }*/
            multiPlayerMenu.startGame(
                    Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                    client, serverName
            );

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            multiPlayerMenu.exit(client);

        } else if (RequestType.HELP.setMatcher(command).find()) {
            multiPlayerMenu.showHelp();

        } else {
            throw new InputException("invalid command");
        }
    }

    private void customGameMenuHandleRequest(Client client, String serverName) throws InputException {
        CustomGameMenu customGameMenu = CustomGameMenu.getInstance();
        if (RequestType.START_GAME.setMatcher(command).find()) {
            Matcher matcher = RequestType.START_GAME.getMatcher();
            if (matcher.group(3) == null) {
                customGameMenu.startGame(
                        matcher.group(1), Integer.parseInt(matcher.group(2)), 0,
                        client, serverName
                );
            } else {
                customGameMenu.startGame(
                        matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                        client, serverName
                );
            }

        } else if (RequestType.HELP.setMatcher(command).find()) {
            customGameMenu.showHelp();

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            customGameMenu.exit(client);

        } else {
            throw new InputException("invalid command");
        }
    }

    private void collectionMenuHandleRequest(Client client, String serverName) throws InputException {
        CollectionMenu collectionMenu = CollectionMenu.getInstance();
        if (RequestType.EXIT.setMatcher(command).find()) {
            collectionMenu.exit(client);

        } else if (RequestType.SEARCH.setMatcher(command).find()) {
            collectionMenu.search(
                    RequestType.SEARCH.getMatcher().group(1), client
            );

        } else if (RequestType.HELP.setMatcher(command).find()) {
            collectionMenu.showHelp();

        } else if (RequestType.SHOW.setMatcher(command).find()) {
            collectionMenu.showItemsAndCards(client);

        } else if (RequestType.CREATE_DECK.setMatcher(command).find()) {
            collectionMenu.newDeck(
                    RequestType.CREATE_DECK.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.DELETE_DECK.setMatcher(command).find()) {
            collectionMenu.removeDeck(
                    RequestType.DELETE_DECK.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.ADD_TO_DECK.setMatcher(command).find()) {
            Matcher matcher = RequestType.ADD_TO_DECK.getMatcher();
            collectionMenu.addCardToDeck(
                    matcher.group(2), matcher.group(1),
                    client, serverName
            );

        } else if (RequestType.REMOVE_FROM_DECK.setMatcher(command).find()) {
            Matcher matcher = RequestType.REMOVE_FROM_DECK.getMatcher();
            collectionMenu.removeCardFromDeck(
                    matcher.group(2), matcher.group(1),
                    client, serverName
            );

        } else if (RequestType.VALIDATE_DECK.setMatcher(command).find()) {
            collectionMenu.validateDeck(
                    RequestType.VALIDATE_DECK.getMatcher().group(1), client
            );

        } else if (RequestType.SELECT_MAIN_DECK.setMatcher(command).find()) {
            collectionMenu.selectDeck(
                    RequestType.SELECT_MAIN_DECK.getMatcher().group(1),
                    client, serverName
            );

        } else if (RequestType.SHOW_ALL_DECKS.setMatcher(command).find()) {
            collectionMenu.showAllDecks(client);

        } else if (RequestType.SHOW_DECK.setMatcher(command).find()) {
            collectionMenu.showDeck(
                    RequestType.SHOW_DECK.getMatcher().group(1), client
            );

        } else if (RequestType.SAVE.setMatcher(command).find()) {
            collectionMenu.save(client, serverName);
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
            mainMenu.exit();

        } else if (RequestType.SAVE.setMatcher(command).find()) {
            mainMenu.save(client, serverName);

        } else if (RequestType.LOGOUT.setMatcher(command).find()) {
            mainMenu.logout(client, serverName);

        } else if (RequestType.SHOW_ACCOUNT.setMatcher(command).find()) {
            mainMenu.showAccount(client);
        } else {
            throw new InputException("invalid command");
        }
    }

    private void battleMenuHandleRequest(Client client, String serverName) throws InputException {
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

    private void accountMenuHandleRequest(Client client, String serverName) throws InputException, ExitCommand {
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

        } else if (RequestType.EXIT.setMatcher(command).find()) {
            accountMenu.exit();
        } else {
            throw new InputException("invalid command");
        }
    }
}
