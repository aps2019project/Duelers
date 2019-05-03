package server.models.message;

import server.models.account.Account;
import server.models.account.AccountInfo;

public class LeaderBoardCopyMessage {
    private AccountInfo[] leaderBoard;

    public LeaderBoardCopyMessage(Account[] leaderBoard) {
        this.leaderBoard = new AccountInfo[leaderBoard.length];
        for (int i = 0; i < leaderBoard.length; i++) {
            this.leaderBoard[i] = new AccountInfo(leaderBoard[i]);
        }
    }
}
