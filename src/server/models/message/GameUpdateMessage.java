package server.models.message;

public class GameUpdateMessage {
    private int turnNumber;
    private int player1CurrentMP;
    private int player1NumberOfCollectedFlags;
    private int player2CurrentMP;
    private int player2NumberOfCollectedFlags;

    public GameUpdateMessage(int turnNumber, int player1CurrentMP, int player1NumberOfCollectedFlags, int player2CurrentMP, int player2NumberOfCollectedFlags) {
        this.turnNumber = turnNumber;
        this.player1CurrentMP = player1CurrentMP;
        this.player1NumberOfCollectedFlags = player1NumberOfCollectedFlags;
        this.player2CurrentMP = player2CurrentMP;
        this.player2NumberOfCollectedFlags = player2NumberOfCollectedFlags;
    }
}
