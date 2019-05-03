package server.models.message;

import server.models.account.Account;
import server.models.account.TempAccount;

public class AccountCopyMessage {
    private TempAccount account;

    public AccountCopyMessage(Account account) {
        this.account = new TempAccount(account);
    }
}
