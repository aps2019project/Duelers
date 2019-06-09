package models.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class NormalSpinner extends Spinner<Integer> {
    private static final Background BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(70, 70, 70), CornerRadii.EMPTY, Insets.EMPTY)
    );
    private static final Border DEFAULT_BORDER = new Border(
            new BorderStroke(Color.gray(0.4), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)
    );
    public NormalSpinner(int min, int max, int initialValue) {
        super(min, max, initialValue);
        getEditor().setFont(UIConstants.DEFAULT_FONT);
        getEditor().setBorder(DEFAULT_BORDER);
        getEditor().setPadding(new Insets(UIConstants.DEFAULT_SPACING * 2));
        getEditor().setBackground(BACKGROUND);
        getEditor().setStyle("-fx-text-inner-color: #fffbfd;");

    }
}
