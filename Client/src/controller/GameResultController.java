package controller;

public class GameResultController {
    private static GameResultController gameResultController;
    private boolean amIWinner;

    public static GameResultController getInstance() {
        if (gameResultController == null) {
            gameResultController = new GameResultController();
        }
        return gameResultController;
    }

    public void setWinner(boolean amIWinner) {
        this.amIWinner = amIWinner;
    }

    public boolean amInWinner() {
        return amIWinner;
    }
}
