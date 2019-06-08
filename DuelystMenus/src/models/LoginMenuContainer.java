package models;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class LoginMenuContainer extends VBox {
    private static final String DUELYST_LOGO_URL = "resources/ui/brand_duelyst.png";

    public LoginMenuContainer() throws FileNotFoundException {
        super(Constants.DEFAULT_SPACING);
        setMaxWidth(Constants.LOGIN_BOX_SIZE * 2);
        setAlignment(Pos.CENTER);

        ImageView brandView = ImageLoader.loadImage(
                DUELYST_LOGO_URL, Constants.DUELYST_LOGO_WIDTH, Constants.DUELYST_LOGO_HEIGHT
        );
        LoginBox horizontalBox = new LoginBox();

        getChildren().addAll(brandView, horizontalBox);
    }
}
