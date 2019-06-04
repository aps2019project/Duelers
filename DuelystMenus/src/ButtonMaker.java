import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

class ButtonMaker {
    private static final Background LOGIN_DEFAULT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(250, 106, 54, 0.8), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final Background LOGIN_HOVER_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(250, 106, 54), CornerRadii.EMPTY, Insets.EMPTY
            )
    );

    static Button makeLoginButton(String s) {
        Button loginButton = new Button(s);
        loginButton.setBackground(
                LOGIN_DEFAULT_BACKGROUND
        );
        loginButton.setPadding(new Insets(Constants.DEFAULT_SPACING * 3));
        loginButton.setPrefWidth(Constants.LOGIN_BOX_SIZE / 2);
        loginButton.setFont(Constants.DEFAULT_FONT);
        loginButton.setTextFill(Color.WHITE);

        loginButton.setOnMouseEntered(event -> {
            loginButton.setBackground(LOGIN_HOVER_BACKGROUND);
            loginButton.setCursor(Cursor.HAND);
        });

        loginButton.setOnMouseExited(event -> {
            loginButton.setBackground(LOGIN_DEFAULT_BACKGROUND);
            loginButton.setCursor(Cursor.DEFAULT);
        });

        return loginButton;
    }
}
