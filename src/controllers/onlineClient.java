package controllers;

import models.account.Account;

public class onlineClient {
    private Client client;
    private Account account;

    public onlineClient(Client client, Account account) {
        this.client = client;
        this.account=account;
    }

    public Client getClient() {
        return this.client;
    }

    public Account getAccount() {
        return this.account;
    }
}