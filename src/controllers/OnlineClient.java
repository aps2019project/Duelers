package controllers;

import models.account.Account;

public class OnlineClient {
    private Client client;
    private Account account;

    public OnlineClient(Client client, Account account) {
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