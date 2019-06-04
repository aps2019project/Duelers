package server.clientPortal.models.message;

import server.detaCenter.models.account.Account;
import server.detaCenter.models.account.AccountInfo;

public class OpponentInfoMessage {
    private AccountInfo opponentInfo;

    public OpponentInfoMessage(Account opponent) {
        this.opponentInfo = new AccountInfo(opponent);
    }
}
