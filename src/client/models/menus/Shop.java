package client.models.menus;

import client.Client;
import client.models.account.Collection;
import client.models.card.Card;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

import java.util.ArrayList;

public class Shop extends Menu {
    private static Shop SHOP;
    private Collection originalCards = new Collection();

    private Shop() {
    }

    public static Shop getInstance(Client client, String serverName) {
        if (SHOP == null) {
            SHOP = new Shop();
            client.addToSendingMessages(
                    Message.makeGetOriginalCardsMessage(
                            client.getClientName(), serverName, 0
                    )
            );
            client.sendMessages();
        }
        return SHOP;
    }

    public static Shop getInstance() {
        return SHOP;
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    public Collection getOriginalCards() {
        return originalCards;
    }

    public void showCollection(Client client) {
        View.getInstance().showCollection(client.getAccount().getCollection());
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

    public void searchInShop(String cardName) throws InputException {
        Card resultCard = originalCards.searchCollection(cardName).get(0);

        View.getInstance().showCardId(resultCard);
    }

    public void searchInCollection(String cardName, Client client) throws InputException {
        ArrayList<Card> resultCards = client.getAccount().getCollection().searchCollection(cardName);

        View.getInstance().showCardIds(resultCards);
    }

    public void showMarketCardsAndItems() {
        View.getInstance().showCollection(originalCards);
    }

    public void setOriginalCards(Collection originalCards) {
        this.originalCards = originalCards;
    }

    @Override
    public void showHelp() {
        String help = "Shop:\n" +
                "\"show collection\"\n" +
                "\"search [item name | card name]\"\n" +
                "\"search collection [item name | card name]\"\n" +
                "\"buy [item name | card name]\"\n" +
                "\"sell [item name | card name]\"\n" +
                "\"show\"\n" +
                "\"exit\"";
        View.getInstance().showHelp(help);
    }
}