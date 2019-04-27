package server.models.account;

public class AccountInfo {
    private String username;
    private int wins;

    public AccountInfo(Account account) {
        this.username = account.getUsername();
        this.wins = account.getWins();
    }
}
