package models.gui;

import controller.SoundEffectPlayer;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import static controller.SoundEffectPlayer.SoundName;

import java.io.FileNotFoundException;

import static models.gui.UIConstants.SCALE;

public class BackButton extends AnchorPane {
    private static final Effect HOVER_EFFECT = new ColorAdjust(0, 0.15, 0.2, 0);
    private static final String BACK_BUTTON_IMAGE_URL = "resources/ui/back_button.png";
    private static final String BACK_TEXT_IMAGE_URL = "resources/ui/back_text.png";
    private static final double SIZE = 400 * SCALE;

    public BackButton(EventHandler<? super MouseEvent> clickEvent) throws FileNotFoundException {
        super();
        ImageView backImage = ImageLoader.loadImage(BACK_BUTTON_IMAGE_URL,
                SIZE, SIZE,
                -SIZE * 0.5, -SIZE * 0.5
        );
        ImageView backText = ImageLoader.loadImage(BACK_TEXT_IMAGE_URL,
                SIZE * 0.25, SIZE * 0.25,
                -UIConstants.DEFAULT_SPACING, -UIConstants.DEFAULT_SPACING
        );

        getChildren().addAll(backImage, backText);

        setOnMouseEntered(event -> {
            setEffect(HOVER_EFFECT);
            SoundEffectPlayer.getInstance().playSound(SoundName.hover);
            setCursor(UIConstants.SELECT_CURSOR);
        });

        setOnMouseExited(event -> {
            setEffect(null);
            setCursor(UIConstants.DEFAULT_CURSOR);
        });

        setOnMouseClicked(clickEvent);
    }
}
