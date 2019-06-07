package controller;

public class MainMenuController {
    private static MainMenuController ourInstanse;
    public static MainMenuController getInstance(){
        if (ourInstanse == null) {
            ourInstanse = new MainMenuController();
        }
        return ourInstanse;
    }
}
