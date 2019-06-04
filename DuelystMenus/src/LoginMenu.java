import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class LoginMenu {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    void show() throws FileNotFoundException {
        Main.setScene(scene);

        BorderPane background = BackgroundMaker.makeMenuBackground();

        BorderPane container = new BorderPane();
        container.setMinSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        container.setMaxSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        BorderPane.setAlignment(container, Pos.CENTER);

        VBox verticalBox = new VBox(Constants.DEFAULT_SPACING);
        verticalBox.setMaxWidth(Constants.LOGIN_BOX_SIZE * 2);
        verticalBox.setAlignment(Pos.CENTER);
        Image brand = new Image(new FileInputStream("resources/ui/brand_duelyst.png"));
        ImageView brandView = new ImageView(brand);
        brandView.setFitWidth(Constants.DUELYST_LOGO_WIDTH);
        brandView.setFitHeight(Constants.DUELYST_LOGO_HEIGHT);

        HBox horizontalBox = new HBox();

        VBox logoBox = new VBox(Constants.DEFAULT_SPACING * 10);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setBackground(new Background(new BackgroundFill(Color.rgb(15, 16, 11), CornerRadii.EMPTY, Insets.EMPTY)));
        logoBox.setMinSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);
        logoBox.setMaxSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);

        ImageView logoView = new ImageView(new Image(new FileInputStream("resources/ui/logo.png")));
        logoView.setFitWidth(Constants.LOGO_WIDTH);
        logoView.setFitHeight(Constants.LOGO_HEIGHT);

        Text text = new Text("LOGIN TO DUELYST USING YOUR ABABEEL ACCOUNT");
        text.setFill(Color.WHITE);
        text.setWrappingWidth(Constants.LOGIN_BOX_SIZE - Constants.DEFAULT_SPACING * 5);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Constants.LOGO_TEXT_FONT);

        logoBox.getChildren().addAll(logoView, text);

        TextField usernameField = TextFieldMaker.makeDefaultTextField("username");
        PasswordField passwordField = TextFieldMaker.makePasswordField();

        Region space = new Region();
        space.setMinHeight(Constants.LOGIN_BOX_SIZE * 0.5);

        HBox buttons = new HBox(Constants.DEFAULT_SPACING);

        Button loginButton = ButtonMaker.makeLoginButton("LOG IN");
        Button registerButton = ButtonMaker.makeLoginButton("REGISTER");

        buttons.getChildren().addAll(loginButton, registerButton);

        VBox loginBox = new VBox(Constants.DEFAULT_SPACING * 2, usernameField, passwordField, space, buttons);
        loginBox.setPadding(Constants.LOGIN_BOX_PADDING);
        loginBox.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        loginBox.setMinSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);
        loginBox.setMaxSize(Constants.LOGIN_BOX_SIZE, Constants.LOGIN_BOX_SIZE);

        horizontalBox.getChildren().addAll(logoBox, loginBox);

        verticalBox.getChildren().addAll(brandView, horizontalBox);

        container.setCenter(verticalBox);

        root.getChildren().addAll(background, container);
    }
}