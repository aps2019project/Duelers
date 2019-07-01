package server.clientPortal.models.message;

import server.detaCenter.models.account.Account;
import server.detaCenter.models.account.AccountInfo;

class LeaderBoardCopyMessage {
    private AccountInfo[] leaderBoard;

    LeaderBoardCopyMessage(Account[] leaderBoard) {
        this.leaderBoard = new AccountInfo[leaderBoard.length];
        for (int i = 0; i < leaderBoard.length; i++) {
            this.leaderBoard[i] = new AccountInfo(leaderBoard[i]);
        }
    }
}
