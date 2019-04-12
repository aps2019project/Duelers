package controllers;

import models.account.Account;

public class OnlineSystem {
    private System system;
    private Account account;

    public OnlineSystem(System system, Account account) {

    }

    public System getSystem() {
        return this.system;
    }

    public Account getAccount() {
        return this.account;
    }
}