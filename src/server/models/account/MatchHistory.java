package server.models.account;

import java.util.Date;

public class MatchHistory {
    private String oppName;
    private boolean amIWinner;
    private Date date;

    public MatchHistory(String oppName, boolean amIWinner) {
        this.oppName = oppName;
        this.amIWinner = amIWinner;
        this.date = new Date(System.currentTimeMillis());
    }

    public String getOppName() {
        return this.oppName;
    }

    public String getDate() {
        return date.toLocaleString();
    }

    public boolean isAmIWinner() {
        return amIWinner;
    }
}