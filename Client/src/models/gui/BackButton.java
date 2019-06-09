package models.gui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;

public class BackButton extends AnchorPane {
    private static final String BACK_BUTTON_IMAGE_URL = "resources/ui/back_button.png";
    private static final String BACK_TEXT_IMAGE_URL = "resources/ui/back_text.png";

    public BackButton(EventHandler<? super MouseEvent> clickEvent) throws FileNotFoundException {
        super();
        ImageView backImage = ImageLoader.loadImage(BACK_BUTTON_IMAGE_URL,
                UIConstants.BACK_BUTTON_SIZE, UIConstants.BACK_BUTTON_SIZE,
                -UIConstants.BACK_BUTTON_SIZE * 0.5, -UIConstants.BACK_BUTTON_SIZE * 0.5
        );
        ImageView backText = ImageLoader.loadImage(BACK_TEXT_IMAGE_URL,
                UIConstants.BACK_BUTTON_SIZE * 0.25, UIConstants.BACK_BUTTON_SIZE * 0.25,
                -UIConstants.DEFAULT_SPACING, -UIConstants.DEFAULT_SPACING
        );

        getChildren().addAll(backImage, backText);

        setOnMouseEntered(event -> {
            setEffect(UIConstants.BACK_BUTTON_HOVER_EFFECT);
            setCursor(UIConstants.SELECT_CURSOR);
        });

        setOnMouseExited(event -> {
            setEffect(null);
            setCursor(UIConstants.DEFAULT_CURSOR);
        });

        setOnMouseClicked(clickEvent);
    }
}
