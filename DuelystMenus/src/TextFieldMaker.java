import javafx.geometry.Insets;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


class TextFieldMaker {
    private static final Background TEXT_FIELD_BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(70, 70, 70), CornerRadii.EMPTY, Insets.EMPTY)
    );
    private static final Border DEFAULT_BORDER = new Border(
            new BorderStroke(Color.gray(0.4), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)
    );

    static TextField makeDefaultTextField(String text) {
        return makeField(text, new TextField());

    }

    static PasswordField makePasswordField() {
        return makeField("password", new PasswordField());
    }

    private static <T extends TextField> T makeField(String text, T textField) {
        textField.setFont(Constants.DEFAULT_FONT);
        textField.setBackground(TEXT_FIELD_BACKGROUND);
        textField.setBorder(DEFAULT_BORDER);
        textField.setPadding(new Insets(Constants.DEFAULT_SPACING * 2));
        textField.setPromptText(text);
        textField.setStyle("-fx-text-inner-color: #fffbfd;");
        return textField;
    }
}
