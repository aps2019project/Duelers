package models.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class PlayButtonBox extends VBox {
    PlayButtonBox(PlayButtonItem item) {
        super(UIConstants.DEFAULT_SPACING * (-3));
        ImageView plate = ImageLoader.makeImageView(
                UIConstants.PLATE_IMAGE, UIConstants.PLAY_MENU_BUTTON_WIDTH, UIConstants.PLAY_MENU_PLATE_HEIGHT
        );

        StackPane imageZone = new PlayButtonImageZone(item);

        getChildren().addAll(imageZone, plate);
        setEffect(UIConstants.PLAY_MENU_BOX_SHADOW);
    }
}
