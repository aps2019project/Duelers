package server.models.account;

public class MatchHistory {

    private String winnerAccount;
    private String loserAccount;
    private int occurrenceTime;

    public MatchHistory(String winnerAccount, String loserAccount) {

    }

    public String getWinnerAccount() {
        return this.winnerAccount;
    }

    public String getLoserAccount() {
        return this.loserAccount;
    }

    public int getOccurrenceTime() {
        return this.occurrenceTime;
    }
}