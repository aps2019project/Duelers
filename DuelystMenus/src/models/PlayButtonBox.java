package models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class PlayButtonBox extends VBox {
    PlayButtonBox(PlayButtonItem item) {
        super(Constants.DEFAULT_SPACING * (-3));
        ImageView plate = ImageLoader.makeImageView(
                Constants.PLATE_IMAGE, Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_PLATE_HEIGHT
        );

        StackPane imageZone = new PlayButtonImageZone(item);

        getChildren().addAll(imageZone, plate);
        setEffect(Constants.PLAY_MENU_BOX_SHADOW);
    }
}
