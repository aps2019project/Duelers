package client.models.menus;

import client.Client;
import client.view.View;

public class GameResultStatus extends Menu{
    private static GameResultStatus ourInstance = new GameResultStatus();
    private boolean amIWinner;

    private GameResultStatus() {
    }

    public static GameResultStatus getInstance() {
        return ourInstance;
    }


    @Override
    public void showHelp() {
        String gameStatus;

        if (amIWinner) {
            gameStatus = "You Won!";
        } else {
            gameStatus = "You Lost!";
        }

        gameStatus += "\n\n\"End game\"";

        View.getInstance().showHelp(gameStatus);
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(MainMenu.getInstance());
    }

    public void setWinner(boolean amIWinner) {
        this.amIWinner = amIWinner;
    }
}
