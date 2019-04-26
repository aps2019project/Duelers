package client.models.account;

public class AccountInfo {
    private String username;
    private int wins;

    public AccountInfo(Account account) {
        this.username = account.getUsername();
        this.wins = account.getWins();
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }
}
