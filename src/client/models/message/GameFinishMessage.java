package client.models.message;

public class GameFinishMessage {
    boolean youWon;

    public boolean amIWinner() {
        return youWon;
    }
}
