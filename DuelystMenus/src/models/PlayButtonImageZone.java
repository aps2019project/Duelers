package models;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class PlayButtonImageZone extends StackPane {
    PlayButtonImageZone(PlayButtonItem item) {
        Space space = new Space(Constants.PLAY_MENU_BUTTON_HEIGHT * 0.75);
        Label title = new PlayButtonTitle(item.title);
        Separator separator = new HorizontalSeparator();
        Text description = new PlayButtonDescription(item.description);

        VBox textBox = new VBox(Constants.DEFAULT_SPACING * 2, space, title, separator, description);
        textBox.setAlignment(Pos.CENTER);

        LinearVignette vignette = new LinearVignette(Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_BUTTON_HEIGHT);

        getChildren().addAll(item.imageView, vignette, textBox);

        setOnMouseEntered(event -> {
            item.imageView.setEffect(Constants.PLAY_MENU_HOVER_EFFECT);
            setCursor(Constants.SELECT_CURSOR);
            title.setEffect(Constants.WHITE_TEXT_SHADOW);
        });

        setOnMouseExited(event -> {
            item.imageView.setEffect(null);
            setCursor(Constants.DEFAULT_CURSOR);
            title.setEffect(null);
        });

        setOnMouseClicked(item.event);
    }
}
