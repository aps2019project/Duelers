package server.models.sorter;

import server.models.account.Account;

import java.util.Comparator;

public class LeaderBoardSorter implements Comparator<Account> {
    @Override
    public int compare(Account o1, Account o2) {
        return o2.getWins() - o1.getWins();
    }
}
