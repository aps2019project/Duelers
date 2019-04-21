package client.models.menus;

public class gameCommands {
    private static gameCommands ourInstance = new gameCommands();

    private gameCommands() {
    }

    public static gameCommands getInstance() {
        return ourInstance;
    }
}
