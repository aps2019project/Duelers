package client.models.menus;

import client.Client;
import client.models.card.Card;
import client.models.card.Deck;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

import java.util.ArrayList;

public class CollectionMenu extends Menu {
    private static CollectionMenu collectionMenu;

    private CollectionMenu() {
    }

    public static CollectionMenu getInstance() {
        if (collectionMenu == null)
            collectionMenu = new CollectionMenu();
        return collectionMenu;
    }

    public void showAllDecks(Client client) {
        View.getInstance().showDecksList(client.getAccount());
    }

    public void showDeck(String deckName, Client client) throws InputException {
        Deck deck = client.getAccount().getDeck(deckName);
        View.getInstance().showDeck(deck);
    }

    public void newDeck(String deckName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeCreateDeckMessage(
                        client.getClientName(), serverName, deckName, client.getAccount().getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void addCardToDeck(String deckName, String cardID, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeAddCardToDeckMessage(
                        client.getClientName(), serverName, deckName, cardID, client.getAccount().getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void removeCardFromDeck(String deckName, String cardID, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeRemoveCardFromDeckMessage(
                        client.getClientName(), serverName, deckName, cardID, client.getAccount().getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void save(Client client, String serverName) {
        client.addToSendingMessages(Message.makeSaveAccountMessage(client.getClientName(), serverName, 0));
        client.sendMessages();
    }

    public void removeDeck(String deckName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeRemoveDeckMessage(
                        client.getClientName(), serverName, deckName, client.getAccount().getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void validateDeck(String deckName, Client client) throws InputException {
        View.getInstance().showDeckValidationMessage(
                client.getAccount().getDeck(deckName).isValid()
        );
    }

    public void selectDeck(String deckName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeSelectDeckMessage(
                        client.getClientName(), serverName, deckName, client.getAccount().getUsername(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    @Override
    public void showHelp() {
        String help = "Collection:\n" +
                "\"show\" : show cards and items\n" +
                "\"create deck [deck name]\"\n" +
                "\"delete deck [deck name]\"\n" +
                "\"search [card name | item name]\"\n" +
                "\"add [card id | hero id] to deck [deck name]\"\n" +
                "\"remove [card id | hero id] from deck [deck name]\"\n" +
                "\"validate deck [deck name]\"\n" +
                "\"select deck [deck name]\"\n" +
                "\"save\"\n" +
                "\"show all decks\"\n" +
                "\"show deck [deck name]\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }

    public void showItemsAndCards(Client client) {
        View.getInstance().showCollection(client.getAccount().getCollection());
    }

    public void search(String cardName, Client client) throws InputException {
        ArrayList<Card> resultCards = client.getAccount().getCollection().searchCollection(cardName);

        View.getInstance().showCardIds(resultCards);
    }
}
