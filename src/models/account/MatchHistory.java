package models.account;

public class MatchHistory {

	private String winner;
	private String loser;
	private int occurrenceTime;

	public MatchHistory(String winner, String loser) {

}

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