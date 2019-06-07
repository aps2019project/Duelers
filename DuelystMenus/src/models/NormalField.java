package models;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class NormalField extends TextField {
    private static final Background TEXT_FIELD_BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(70, 70, 70), CornerRadii.EMPTY, Insets.EMPTY)
    );
    private static final Border DEFAULT_BORDER = new Border(
            new BorderStroke(Color.gray(0.4), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)
    );
    NormalField(String text) {
        setFont(Constants.DEFAULT_FONT);
        setBackground(TEXT_FIELD_BACKGROUND);
        setBorder(DEFAULT_BORDER);
        setPadding(new Insets(Constants.DEFAULT_SPACING * 2));
        setPromptText(text);
        setStyle("-fx-text-inner-color: #fffbfd;");
    }
}
