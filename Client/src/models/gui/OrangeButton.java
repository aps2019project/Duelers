package models.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

import static models.gui.UIConstants.SCALE;

public class OrangeButton extends Button {
    private static final Background DEFAULT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(250, 106, 54, 0.8), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final Background HOVER_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(227, 55, 60), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final double WIDTH = 400 * SCALE;

    public OrangeButton(String text, EventHandler<? super MouseEvent> clickEvent) {
        super(text);
        setBackground(DEFAULT_BACKGROUND);
        setPadding(new Insets(UIConstants.DEFAULT_SPACING * 3));
        setPrefWidth(WIDTH);
        setFont(UIConstants.DEFAULT_FONT);
        setTextFill(Color.WHITE);

        setOnMouseEntered(event -> {
            setBackground(HOVER_BACKGROUND);
            setCursor(UIConstants.SELECT_CURSOR);
        });

        setOnMouseExited(event -> {
            setBackground(DEFAULT_BACKGROUND);
            setCursor(UIConstants.DEFAULT_CURSOR);
        });

        setOnMouseClicked(clickEvent);
    }

    OrangeButton(String text, EventHandler<? super MouseEvent> event, String graphicUrl) throws FileNotFoundException {
        this(text, event);
        setGraphic(ImageLoader.loadImage(graphicUrl, 50, 50));
    }
}
