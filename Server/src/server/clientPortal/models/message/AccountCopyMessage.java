package server.clientPortal.models.message;

import server.detaCenter.models.account.Account;
import server.detaCenter.models.account.TempAccount;

public class AccountCopyMessage {
    private TempAccount account;

    public AccountCopyMessage(Account account) {
        this.account = new TempAccount(account);
    }
}
