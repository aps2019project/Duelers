package client.models.menus;

import client.Client;
import client.models.map.Position;
import client.models.message.Message;
import client.view.View;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();
    private String selectedItem;
    private boolean isInGraveYard;
    private String selectedCardId;

    private GameCommands() {
    }


    public static GameCommands getInstance() {
        return ourInstance;
    }

    @Override
    public void exit(Client client) {

    }

    @Override
    public void showHelp() {
    }

    public void showGameActions() {

    }

    public void showGameInfo() {

    }

    public void showMyMinions() {
    }

    public void showOppMinions() {

    }

    public void showCardInfo(String cardId) {

    }

    public void selectCard(String cardId) {
        selectedCardId = cardId;
    }

    public void move(Client client , String serverName,int row, int column) {
        Message message = Message.makeMoveTroopMessage(client.getClientName(),serverName,selectedCardId,new Position(row,column),0);
        client.addToSendingMessages(message);
        client.sendMessages();

    }

    public void attack(String cardId) {

    }

    public void attackCombo(String oppCardId, String[] cardIds) {

    }

    public void useSpecialPower( Client client , String serverName,int row, int column) {
    }

    public void showHand(Client client) {
        View.getInstance().showHand(client.getGame().getPlayerOne());
    }

    public void insertCard(Client client, String serverName, String cardId, int row, int column) {
        Message message = Message.makeInsertMessage(
                client.getClientName(), serverName, cardId, new Position(row, column), 0
        );
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void endTurn(Client client, String serverName) {
        Message message = Message.makeEndTurnMessage(client.getClientName(), serverName, 0);
        client.addToSendingMessages(message);
        client.sendMessages();
    }

    public void show‫‪Collectables‬‬() {

    }

    public void selectItem(String itemID) {

    }

    public void showNextCard() {

    }

    public boolean isItemSelected() {
        return selectedItem != null;
    }

    public void showSelectedItemInfo() {

    }

    public void useItem(int row, int column) {

    }

    public boolean isInGraveYard() {
        return isInGraveYard;
    }

    public void enterGraveYard() {

    }

    public void showCardInfoInGraveYard(String cardId) {

    }

    public void exitFromGraveYard() {
        isInGraveYard = false;
    }

    public void showCardsInGraveYard() {

    }

    public void endGame() {

    }

    public boolean selectCard(){
        return  selectedCardId != null;


    }
}
