package client.models.menus;

import client.Client;
import client.models.account.Collection;
import client.models.card.Deck;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class CollectionMenu implements Menu {
    private static CollectionMenu collectionMenu;

    private CollectionMenu() {
    }

    public static CollectionMenu getInstance() {
        if (collectionMenu == null)
            collectionMenu = new CollectionMenu();
        return collectionMenu;
    }

    public void showAllDecks(Client client){
        View.getInstance().showDecksList(client.getAccount().getDecks());
    }

    public void showDeck(String deckName, Client client) throws InputException {
        Deck deck = client.getAccount().getDeck(deckName);
        View.getInstance().showDeck(deck);
    }

    public void newDeck(String deckName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeCreateDeckMessage(
                        client.getClientName(), serverName, deckName, 0
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
                        client.getClientName(), serverName, deckName, cardID, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    public void removeCardFromDeck(String deckName, String cardID) {

    }

    public void save() {

    }

    public void removeDeck(String deckName) {

    }

    public void validateDeck(String deckName) {

    }

    public void selectDeck(String DeckName) {

    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    public void showHelp() {
        String help = "\"show\" : show cards and items\n" +
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

    public void showItemsAndCards(){

    }

    public void search(String cardID){

    }

}
