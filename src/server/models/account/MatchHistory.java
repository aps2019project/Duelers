package server.models.account;

public class MatchHistory {
    private String accountOne;
    private boolean oneIsWinner;
    private String accountTwo;
    private boolean twoIsWinner;
    private int occurrenceTime;

    public MatchHistory(String accountOne, boolean oneIsWinner, String accountTwo, boolean twoIsWinner) {
        this.accountOne = accountOne;
        this.oneIsWinner = oneIsWinner;
        this.accountTwo = accountTwo;
        this.twoIsWinner = twoIsWinner;
    }

    public MatchHistory(String accountOne, String accountTwo) {

    }

    public String getAccountOne() {
        return this.accountOne;
    }

    public String getAccountTwo() {
        return this.accountTwo;
    }

    public int getOccurrenceTime() {
        return this.occurrenceTime;
    }

    public boolean isOneIsWinner() {
        return oneIsWinner;
    }

    public boolean isTwoIsWinner() {
        return twoIsWinner;
    }
}