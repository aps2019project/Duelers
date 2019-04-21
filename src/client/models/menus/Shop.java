package client.models.menus;

import client.Client;
import client.models.account.Account;
import client.models.card.Card;
import client.models.message.Message;
import client.view.View;

import java.util.ArrayList;

public class Shop implements Menu {
    private static Shop SHOP;
    private ArrayList<Card> cards;

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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void showCollection(Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeShowShopMessage(
                        client.getClientName(), serverName, 0
                )
        );
        client.sendMessages();
    }

    public void buy(String cardName, Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeBuyCardMessage(
                        client.getClientName(), serverName, cardName, 0
                )
        );
        client.sendMessages();
    }

    public void sell(String cardId, Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeSellCardMessage(
                        client.getClientName(), serverName, cardId, 0
                )
        );
        client.sendMessages();
    }

    public void searchCard(String cardName, Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeShopSearchMessage(
                        client.getClientName(), serverName, cardName, 0
                )
        );
        client.sendMessages();
    }

    public void searchCollection(String cardName, Client client, String serverName) {
        client.addToSendingMessages(
                Message.makeCollectionSearchMessage(
                        client.getClientName(), serverName, cardName, client.getAccount().getUserName(), 0
                )
        );
        client.sendMessages();
    }

    public void showMarketCardsAndItems(Client client) {

    }

    public void showHelp() {
        String help = "\"show collection\"\n" +
                "\"search [item name | card name]\"\n" +
                "\"search collection [item name | card name]\"\n" +
                "\"buy [item name | card name]\"\n" +
                "\"sell [item name | card name]\"\n" +
                "\"show\"\n" +
                "\"exit\"";
        View.getInstance().printHelp(help);
    }
}