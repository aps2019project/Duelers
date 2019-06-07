import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;

class BackButton extends AnchorPane {
    private static final String BACK_BUTTON_IMAGE_URL = "resources/ui/back_button.png";
    private static final String BACK_TEXT_IMAGE_URL = "resources/ui/back_text.png";

    BackButton(EventHandler<? super MouseEvent> clickEvent) throws FileNotFoundException {
        super();
        ImageView backImage = ImageLoader.loadImage(BACK_BUTTON_IMAGE_URL,
                Constants.BACK_BUTTON_SIZE, Constants.BACK_BUTTON_SIZE,
                -Constants.BACK_BUTTON_SIZE * 0.5, -Constants.BACK_BUTTON_SIZE * 0.5
        );
        ImageView backText = ImageLoader.loadImage(BACK_TEXT_IMAGE_URL,
                Constants.BACK_BUTTON_SIZE * 0.25, Constants.BACK_BUTTON_SIZE * 0.25,
                -Constants.DEFAULT_SPACING, -Constants.DEFAULT_SPACING
        );

        getChildren().addAll(backImage, backText);

        setOnMouseEntered(event -> {
            setEffect(Constants.BACK_BUTTON_HOVER_EFFECT);
            setCursor(Constants.SELECT_CURSOR);
        });

        setOnMouseExited(event -> {
            setEffect(null);
            setCursor(Constants.DEFAULT_CURSOR);
        });

        setOnMouseClicked(clickEvent);
    }
}
