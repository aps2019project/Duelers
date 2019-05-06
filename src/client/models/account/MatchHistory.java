package client.models.account;

import java.util.Date;

public class MatchHistory {
    private String oppName;
    private boolean amIWinner;
    private Date date;

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