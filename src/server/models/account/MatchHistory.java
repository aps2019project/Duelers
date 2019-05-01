package server.models.account;

import java.util.Date;

public class MatchHistory {
    private String oppName;
    private boolean amIWinner;
    private long occurrenceTime;

    public MatchHistory(String oppName, boolean amIWinner) {
        this.oppName = oppName;
        this.amIWinner = amIWinner;
        this.occurrenceTime = System.currentTimeMillis();
    }

    public String getOppName() {
        return this.oppName;
    }

    public long getExactTime() {
        return this.occurrenceTime;
    }

    public String getDate() {
        Date date = new Date();
        new Date().setTime(occurrenceTime);
        return date.toLocaleString();
    }

    public boolean isAmIWinner() {
        return amIWinner;
    }
}