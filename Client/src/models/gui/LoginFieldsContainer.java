package models.gui;

import controller.LoginMenuController;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class LoginFieldsContainer extends VBox {
    private static final Background LOGIN_BOX_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(100, 100, 100, 0.6), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private final NormalField usernameField;
    private final NormalPasswordField passwordField;

    LoginFieldsContainer() {
        super(UIConstants.DEFAULT_SPACING * 2);
        usernameField = new NormalField("username");
        passwordField = new NormalPasswordField();

        Region space = new Region();
        space.setMinHeight(UIConstants.LOGIN_BOX_SIZE * 0.5);

        HBox buttons = makeButtonsBox();

        getChildren().addAll(usernameField, passwordField, space, buttons);
        setPadding(UIConstants.LOGIN_BOX_PADDING);
        setBackground(LOGIN_BOX_BACKGROUND);
        setMinSize(UIConstants.LOGIN_BOX_SIZE, UIConstants.LOGIN_BOX_SIZE);
        setMaxSize(UIConstants.LOGIN_BOX_SIZE, UIConstants.LOGIN_BOX_SIZE);
    }

    private HBox makeButtonsBox() {
        OrangeButton loginButton = new OrangeButton("LOG IN",
                event -> LoginMenuController.getInstance().login(usernameField.getText(), passwordField.getText())
        );
        OrangeButton registerButton = new OrangeButton("REGISTER",
                event -> LoginMenuController.getInstance().register(usernameField.getText(), passwordField.getText())
        );

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                LoginMenuController.getInstance().login(usernameField.getText(), passwordField.getText());
            }
        });

        return new HBox(UIConstants.DEFAULT_SPACING, loginButton, registerButton);
    }
}
