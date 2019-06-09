package models.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class PlayButtonDescription extends Text {
    PlayButtonDescription(String description) {
        super(description);
        setFont(UIConstants.MENU_HINT_FONT);
        setFill(Color.WHITE);
        setTextAlignment(TextAlignment.CENTER);
        setWrappingWidth(UIConstants.PLAY_MENU_BUTTON_WIDTH - UIConstants.DEFAULT_SPACING * 2);
    }
}
