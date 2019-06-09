package models.gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class DialogContainer extends BorderPane {
    private static final Background BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(0, 0, 0, 0.4), CornerRadii.EMPTY, Insets.EMPTY)
    );
    private final AnchorPane root;

    public DialogContainer(AnchorPane root, Node center) {
        this.root = root;
        setMinSize(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
        setBackground(BACKGROUND);
        setCenter(center);
    }

    public void makeClosable(AtomicBoolean shouldBeClosed) {
        setOnMouseClicked(event -> {
            if (!shouldBeClosed.get()) {
                shouldBeClosed.set(true);
                return;
            }
            close();
        });
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });
    }

    public void show(AnchorPane root) {
        root.getChildren().get(0).setEffect(new GaussianBlur(UIConstants.ON_DIALOG_BLUR));
        root.getChildren().add(this);
    }

    public void close() {
        root.getChildren().remove(this);
        root.getChildren().get(0).setEffect(null);
    }
}
