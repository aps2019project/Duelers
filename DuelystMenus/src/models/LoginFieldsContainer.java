package models;

import view.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class LoginFieldsContainer extends VBox {
    private static final Background LOGIN_BOX_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(100, 100, 100, 0.6), CornerRadii.EMPTY, Insets.EMPTY
            )
    );

    LoginFieldsContainer() {
        super(Constants.DEFAULT_SPACING * 2);
        NormalField usernameField = new NormalField("username");
        NormalPasswordField passwordField = new NormalPasswordField();

        Region space = new Region();
        space.setMinHeight(Constants.LOGIN_BOX_SIZE * 0.5);

        HBox buttons = makeButtonsBox();

        getChildren().addAll(usernameField, passwordField, space, buttons);
        setPadding(Constants.LOGIN_BOX_PADDING);
        setBackground(LOGIN_BOX_BACKGROUND);
        setMinSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);
        setMaxSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);
    }

    private HBox makeButtonsBox() {
        OrangeButton loginButton = new OrangeButton("LOG IN", event -> MainMenu.getInstance().show());
        OrangeButton registerButton = new OrangeButton("REGISTER", event -> {});
        return new HBox(Constants.DEFAULT_SPACING, loginButton, registerButton);
    }
}
