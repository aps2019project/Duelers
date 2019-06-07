package models;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

class PlayButtonTitle extends Label {
    PlayButtonTitle(String title) {
        super(title);
        setFont(Constants.PLAY_MENU_ITEM_FONT);
        setTextFill(Color.WHITE);
    }
}
