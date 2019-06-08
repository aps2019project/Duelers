package models;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class PlayButtonDescription extends Text {
    PlayButtonDescription(String description) {
        super(description);
        setFont(Constants.MENU_HINT_FONT);
        setFill(Color.WHITE);
        setTextAlignment(TextAlignment.CENTER);
        setWrappingWidth(Constants.PLAY_MENU_BUTTON_WIDTH - Constants.DEFAULT_SPACING * 2);
    }
}
