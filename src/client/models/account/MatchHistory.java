package client.models.account;

public class MatchHistory {
	private String winner;
	private String loser;
	private int occurrenceTime;

	public String getWinner() {
		return this.winner;
	}

	public String getLoser() {
		return this.loser;
	}

	public int getOccurrenceTime() {
		return this.occurrenceTime;
	}
}