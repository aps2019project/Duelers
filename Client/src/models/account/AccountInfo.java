package models.account;

public class AccountInfo {
    private String username;
    private boolean online;
    private int wins;
    private AccountType type;

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public int getWins() {
        return wins;
    }

    public AccountType getType() {
        return type;
    }
}
