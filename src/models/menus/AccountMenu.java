package models.menus;

import controllers.System;

public class AccountMenu extends Menu {
    private static final AccountMenu ACCOUNT_MENU = new AccountMenu();

    private AccountMenu() {
    }

    public static AccountMenu getInstance() {
        return ACCOUNT_MENU;
    }

    public void register(String userName, String password) {

    }

    public void login(String userName, String password, System system) {

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