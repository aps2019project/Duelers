package server.models.message;

import server.models.account.Account;
import server.models.account.AccountInfo;

public class OpponentInfoMessage {
    private AccountInfo opponentInfo;

    public OpponentInfoMessage(Account opponent) {
        this.opponentInfo = new AccountInfo(opponent);
    }
}
