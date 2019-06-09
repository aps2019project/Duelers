package models.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileNotFoundException;

class AbabeelLogoBox extends VBox {
    private static final String ABABEEL_URL = "resources/ui/logo.png";
    private static final Paint ABABEEL_TEXT_COLOR = Color.rgb(102, 166, 214);
    private static final Background ABABEEL_BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(15, 16, 11), CornerRadii.EMPTY, Insets.EMPTY)
    );

    AbabeelLogoBox() throws FileNotFoundException {
        super(UIConstants.DEFAULT_SPACING * 10);
        ImageView logoView = ImageLoader.loadImage(ABABEEL_URL, UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT);
        Text text = makeAbabeelText();

        getChildren().addAll(logoView, text);
        setAlignment(Pos.CENTER);
        setBackground(ABABEEL_BACKGROUND);
        setMinSize(UIConstants.LOGIN_BOX_SIZE, UIConstants.LOGIN_BOX_SIZE);
        setMaxSize(UIConstants.LOGIN_BOX_SIZE, UIConstants.LOGIN_BOX_SIZE);
    }

    private Text makeAbabeelText() {
        Text text = new Text("LOGIN TO DUELYST USING YOUR ABABEEL ACCOUNT");
        text.setFill(ABABEEL_TEXT_COLOR);
        text.setWrappingWidth(UIConstants.LOGIN_BOX_SIZE - UIConstants.DEFAULT_SPACING * 5);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(UIConstants.LOGO_TEXT_FONT);
        return text;
    }
}
