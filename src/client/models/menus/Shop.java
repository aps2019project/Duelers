package client.models.menus;

import client.Client;
import client.models.account.Collection;
import client.models.card.Card;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

import java.util.ArrayList;

public class Shop implements Menu {
    private static Shop SHOP;
    private ArrayList<Card> shop;
    private Collection collection;
    private ArrayList<Card> resultCards;
    private Card resultCard;

    private Shop() {
    }

    public static Shop getInstance() {
        if (SHOP == null) {
            SHOP = new Shop();
        }
        return SHOP;
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    public ArrayList<Card> getShopCards() {
        return shop;
    }

    public void showCollection(Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeGetCollectionMessage(
                        client.getClientName(), serverName, client.getAccount().getUserName(), 0
                )
        );
        client.sendMessages();
        View.getInstance().showCollection(collection);
    }

    public void buy(String cardName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeBuyCardMessage(
                        client.getClientName(), serverName, cardName, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
        View.getInstance().showSuccessfulBuyMessage();
    }

    public void sell(String cardId, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeSellCardMessage(
                        client.getClientName(), serverName, cardId, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
        View.getInstance().showSuccessfulSellMessage();
    }

    public void searchInShop(String cardName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeShopSearchMessage(
                        client.getClientName(), serverName, cardName, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
        View.getInstance().showCardId(resultCard);
    }

    public void searchInCollection(String cardName, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeCollectionSearchMessage(
                        client.getClientName(), serverName, cardName, client.getAccount().getUserName(), 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
        View.getInstance().showCardIds(resultCards);
    }

    public void showMarketCardsAndItems() {
        View.getInstance().showCardsList(shop);
    }

    public void showHelp() {
        String help = "\"show collection\"\n" +
                "\"search [item name | card name]\"\n" +
                "\"search collection [item name | card name]\"\n" +
                "\"buy [item name | card name]\"\n" +
                "\"sell [item name | card name]\"\n" +
                "\"show\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }
}