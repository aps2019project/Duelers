package client.models.menus;

import client.Client;

public class GameCommands extends Menu {
    private static GameCommands ourInstance = new GameCommands();
    private String selectedItem;
    private boolean isInGraveYard;

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

    public void showGameActions(){

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

    }

    public void move(int row, int column) {

    }

    public void attack(String cardId) {

    }

    public void attackCombo(String oppCardId, String[] cardIds) {

    }

    public void useSpecialPower(int row, int column) {

    }

    public void showHand() {

    }

    public void insertCard(String cardId, int row, int column) {

    }

    public void endTurn() {

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
}
