package models.menus;

import controllers.Client;

public class AccountMenu extends Menu {
    private static AccountMenu ACCOUNT_MENU;

    private AccountMenu() {
    }

    public static AccountMenu getInstance()
    {
        if (ACCOUNT_MENU == null) {
             ACCOUNT_MENU = new AccountMenu();
        }
        return ACCOUNT_MENU;
    }

    public void register(String userName, String password) {

    }

    public void login(String userName, String password, Client client) {

    }

    public void updateLeaderBoard() {

    }

    public void showLeaderBoard() {

    }

    public void save() {

    }

    public void logout() {

    }
}