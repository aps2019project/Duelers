import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

class CustomGameMenu extends PlayMenu {
    private static final String BACKGROUND_URL = "resources/menu/background/custom_game_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> SinglePlayerMenu.getInstance().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/kill_hero.jpg", "KILL HERO",
                    "You must kill opponent's hero to win", event -> {
            }),
            new PlayButtonItem("resources/menu/playButtons/single_flag.jpg", "SINGLE FLAG",
                    "You must keep the flag for 6 turns to win", event -> {
            }),
            new PlayButtonItem("resources/menu/playButtons/multi_flag.jpg", "MULTI FLAG",
                    "You must collect at least half the flags to win", event -> {
            })
    };
    private static CustomGameMenu menu;

    private CustomGameMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
    }

    static CustomGameMenu getInstance() {
        if (menu == null) {
            try {
                menu = new CustomGameMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
