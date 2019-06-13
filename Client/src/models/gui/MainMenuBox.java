package models.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class MainMenuBox extends VBox {
    private static final String DUELYST_LOGO_URL = "resources/ui/brand_duelyst.png";

    public MainMenuBox(MenuItem[] items) throws FileNotFoundException {
        super(UIConstants.DEFAULT_SPACING * 5);
        ImageView brandView = ImageLoader.loadImage(
                DUELYST_LOGO_URL, UIConstants.DUELYST_LOGO_WIDTH, UIConstants.DUELYST_LOGO_HEIGHT
        );
        MainMenuGrid menuGrid = new MainMenuGrid(items);

        getChildren().addAll(brandView, menuGrid);
        setAlignment(Pos.CENTER);
        relocate(UIConstants.MAIN_MENU_BOX_X, UIConstants.MAIN_MENU_BOX_Y);
        setPadding(new Insets(UIConstants.DEFAULT_SPACING * 6));
    }
}
