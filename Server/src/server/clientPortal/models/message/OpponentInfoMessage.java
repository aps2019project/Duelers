package server.clientPortal.models.message;

import server.detaCenter.models.account.Account;
import server.detaCenter.models.account.AccountInfo;

class OpponentInfoMessage {
    private AccountInfo opponentInfo;

    OpponentInfoMessage(Account opponent) {
        this.opponentInfo = new AccountInfo(opponent);
    }
}
