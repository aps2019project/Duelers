import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

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
    private static final String BACK_BUTTON_IMAGE_URL = "resources/ui/back_button.png";
    private static final String BACK_TEXT_IMAGE_URL = "resources/ui/back_text.png";

    static Button makeLoginButton(String text, EventHandler<? super MouseEvent> clickEvent) {
        Button button = new Button(text);
        button.setBackground(
                LOGIN_DEFAULT_BACKGROUND
        );
        button.setPadding(new Insets(Constants.DEFAULT_SPACING * 3));
        button.setPrefWidth(Constants.LOGIN_BOX_SIZE / 2);
        button.setFont(Constants.DEFAULT_FONT);
        button.setTextFill(Color.WHITE);

        button.setOnMouseEntered(event -> {
            button.setBackground(LOGIN_HOVER_BACKGROUND);
            button.setCursor(Constants.SELECT_CURSOR);
        });

        button.setOnMouseExited(event -> {
            button.setBackground(LOGIN_DEFAULT_BACKGROUND);
            button.setCursor(Constants.DEFAULT_CURSOR);
        });

        button.setOnMouseClicked(clickEvent);
        return button;
    }
    
    static AnchorPane makeBackButton(EventHandler<? super MouseEvent> clickEvent) throws FileNotFoundException {
        ImageView backImage = ImageLoader.loadImage(BACK_BUTTON_IMAGE_URL,
                Constants.BACK_BUTTON_SIZE, Constants.BACK_BUTTON_SIZE,
                -Constants.BACK_BUTTON_SIZE * 0.5, -Constants.BACK_BUTTON_SIZE * 0.5
        );
        ImageView backText = ImageLoader.loadImage(BACK_TEXT_IMAGE_URL,
                Constants.BACK_BUTTON_SIZE * 0.25, Constants.BACK_BUTTON_SIZE * 0.25,
                -Constants.DEFAULT_SPACING, -Constants.DEFAULT_SPACING
        );

        AnchorPane backButton = new AnchorPane(backImage, backText);

        backButton.setOnMouseEntered(event -> {
            backButton.setEffect(Constants.BACK_BUTTON_HOVER_EFFECT);
            backButton.setCursor(Constants.SELECT_CURSOR);
        });

        backButton.setOnMouseExited(event -> {
            backButton.setEffect(null);
            backButton.setCursor(Constants.DEFAULT_CURSOR);
        });

        backButton.setOnMouseClicked(clickEvent);
        return backButton;
    }
}
