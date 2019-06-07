package models;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

class OrangeButton extends Button {
    private static final Background DEFAULT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(250, 106, 54, 0.8), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final Background HOVER_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(250, 106, 54), CornerRadii.EMPTY, Insets.EMPTY
            )
    );

    OrangeButton(String text, EventHandler<? super MouseEvent> clickEvent) {
        super(text);
        setBackground(
                DEFAULT_BACKGROUND
        );
        setPadding(new Insets(Constants.DEFAULT_SPACING * 3));
        setPrefWidth(Constants.LOGIN_BOX_SIZE / 2);
        setFont(Constants.DEFAULT_FONT);
        setTextFill(Color.WHITE);

        setOnMouseEntered(event -> {
            setBackground(HOVER_BACKGROUND);
            setCursor(Constants.SELECT_CURSOR);
        });

        setOnMouseExited(event -> {
            setBackground(DEFAULT_BACKGROUND);
            setCursor(Constants.DEFAULT_CURSOR);
        });

        setOnMouseClicked(clickEvent);
    }
}
