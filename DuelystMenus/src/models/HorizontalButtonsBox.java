package models;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HorizontalButtonsBox extends HBox {
    public HorizontalButtonsBox(PlayButtonItem[] items) {
        super(Constants.DEFAULT_SPACING * 8);
        setAlignment(Pos.CENTER);
        setMaxHeight(Constants.PLAY_MENU_BUTTON_HEIGHT + Constants.PLAY_MENU_PLATE_HEIGHT);

        for (PlayButtonItem item : items) {
            PlayButtonBox buttonBox = new PlayButtonBox(item);
            getChildren().add(buttonBox);
        }
    }
}
