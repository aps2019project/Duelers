import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

class DialogContainer extends BorderPane {
    private static final Background BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(0, 0, 0, 0.4), CornerRadii.EMPTY, Insets.EMPTY)
    );

    DialogContainer(Node center) {
        setMinSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        setBackground(BACKGROUND);
        setCenter(center);
    }

    void exitOnMouseClick(AtomicBoolean shouldBeClosed, AnchorPane root) {
        setOnMouseClicked(event -> {
            if (!shouldBeClosed.get()) {
                shouldBeClosed.set(true);
                return;
            }
            closeFrom(root);
        });
    }

    void closeFrom(AnchorPane root) {
        root.getChildren().remove(this);
        root.getChildren().get(0).setEffect(null);
    }
}
