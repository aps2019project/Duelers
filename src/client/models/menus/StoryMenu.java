package client.models.menus;

public class StoryMenu extends Menu {
    private static StoryMenu ourInstance = new StoryMenu();

    private StoryMenu() {
    }

    public static StoryMenu getInstance() {
        return ourInstance;
    }

}
