package client.models.menus;

import client.Client;
import client.models.account.Account;
import client.models.card.Card;
import client.view.View;

import java.util.ArrayList;

public class Shop extends Menu {
    private static  Shop SHOP ;
    private ArrayList<Card> cards;

    private Shop() {
    }

    public static Shop getInstance() {
        if (SHOP == null) {
            SHOP = new Shop();
        }
        return SHOP;
    }

    public void returnToMain(Client client){
        client.setCurrentMenu(MainMenu.getInstance());
    }
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void showCollection(){

    }

    public void buy(Client client,String cardName){

    }

    public void buy(Account account, Card card) {

    }

    public void sell(Account account, Card card) {

    }

    public void searchCard(String cardName) {
    }

    public void searchCollection(String collectionName) {

    }

    public void sell(Client client, String cardName) {

    }

    public void showMarketCardsAndItems(Client client){

    }

    public void showHelp(){
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