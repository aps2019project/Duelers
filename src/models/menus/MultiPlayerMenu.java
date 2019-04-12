package models.menus;

import models.account.Account;

public class MultiPlayerMenu extends Menu {
    private static final MultiPlayerMenu MULTI_PLAYER_MENU = new MultiPlayerMenu();
    private Account secondAccount;
    private MultiPlayerMenu() {

    }

    public static MultiPlayerMenu getInstance() {
        return MULTI_PLAYER_MENU;
    }

    public void selectUser(String userName) {

    }

    public void startGame(String gameDetails) {

    }
}
