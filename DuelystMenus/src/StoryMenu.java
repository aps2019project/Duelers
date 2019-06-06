import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

class StoryMenu extends PlayMenu {
    private static final String BACKGROUND_URL = "resources/menu/background/story_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> SinglePlayerMenu.getInstance().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/kill_hero.jpg", "FIRST STAGE",
                    "You must kill opponent's hero to win", event -> {}),
            new PlayButtonItem("resources/menu/playButtons/single_flag.jpg", "SECOND STAGE",
                    "You must keep the flag for 6 turns to win", event -> {}),
            new PlayButtonItem("resources/menu/playButtons/multi_flag.jpg", "THIRD STAGE",
                    "You must collect at least half the flags to win", event -> {})
    };
    private static StoryMenu menu;

    private StoryMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
    }

    static StoryMenu getInstance() {
        if (menu == null) {
            try {
                menu = new StoryMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
