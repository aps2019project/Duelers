import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

class SinglePlayerMenu extends PlayMenu {
    private static final String BACKGROUND_URL = "resources/menu/background/single_player_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> PlayMenu.getInstance().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/story.jpg", "STORY GAME",
                    "Play in a story of duelyst", event -> {}),
            new PlayButtonItem("resources/menu/playButtons/custom_game.jpg", "CUSTOM GAME",
                    "Play with one of your decks controlled by AI", event -> {})
    };
    private static SinglePlayerMenu menu;

    private SinglePlayerMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
    }

    static SinglePlayerMenu getInstance() {
        if (menu == null) {
            try {
                menu = new SinglePlayerMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
