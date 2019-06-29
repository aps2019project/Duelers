package models.gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static models.gui.UIConstants.SCALE;

public class ImageButton extends StackPane {
    private static final double WIDTH = 348 * SCALE;
    private static final double HEIGHT = 108 * SCALE;
    private static final Font FONT = Font.font("SansSerif", FontWeight.BOLD, 35 * SCALE);
    private static Image defaultImage;
    private static Image hoverImage;

    static {
        try {
            defaultImage = new Image(new FileInputStream("resources/ui/button_primary@2x.png"));
            hoverImage= new Image(new FileInputStream("resources/ui/button_primary_glow@2x.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ImageButton(String text, EventHandler<? super MouseEvent> mouseEvent) {
        this(text, mouseEvent, defaultImage, hoverImage);
    }

    public ImageButton(String text, EventHandler<? super MouseEvent> mouseEvent, Image defaultImage, Image hoverImage) {
        ImageView imageView = ImageLoader.makeImageView(defaultImage, WIDTH, HEIGHT);
        DefaultLabel label = new DefaultLabel(text, FONT, Color.WHITE);

        setOnMouseEntered(event -> {
            imageView.setImage(hoverImage);
            setCursor(UIConstants.SELECT_CURSOR);
        });
        setOnMouseExited(event -> {
            imageView.setImage(defaultImage);
            setCursor(UIConstants.DEFAULT_CURSOR);
        });
        setOnMouseClicked(mouseEvent);

        getChildren().addAll(imageView, label);
    }
}
