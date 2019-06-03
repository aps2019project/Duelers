package server.gameCenter;

public class GameCenter {
    private static GameCenter ourInstance = new GameCenter();

    public static GameCenter getInstance() {
        return ourInstance;
    }

    private GameCenter() {
    }
}
