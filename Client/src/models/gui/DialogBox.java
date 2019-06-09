package models.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class DialogBox extends VBox {
    private static final Background DIALOG_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(255, 200, 200, 0.6), new CornerRadii(UIConstants.DEFAULT_SPACING * 3), Insets.EMPTY
            )
    );

    public DialogBox(Node... children) {
        super(UIConstants.DEFAULT_SPACING * 4, children);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(UIConstants.DEFAULT_SPACING * 6));
        setBackground(DIALOG_BACKGROUND);
        setMaxSize(UIConstants.DIALOG_MAX_WIDTH, UIConstants.DIALOG_MAX_HEIGHT);
        setEffect(UIConstants.PLAY_MENU_BOX_SHADOW);
    }

    public void setButtonMouseEvent(EventHandler<? super MouseEvent> event) {
        getChildren().add(new OrangeButton("SELECT", event));
    }

    public void preventClosingOnClick(AtomicBoolean shouldBeClosed) {
        setOnMouseClicked(event -> shouldBeClosed.set(false));
    }
}
