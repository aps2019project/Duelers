package server.clientPortal.models.message;

public class GameFinishMessage {
    boolean youWon;

    public GameFinishMessage(boolean youWon) {
        this.youWon = youWon;
    }
}
