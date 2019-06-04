import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class Constants {
    static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(44, 33, 129), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final double SCALE = 0.7;
    static final double MAIN_MENU_BOX_X = 100 * SCALE;
    static final double MAIN_MENU_BOX_Y = 400 * SCALE;
    static final double MENU_HINT_WIDTH = 250 * SCALE;
    static final double MENU_ITEM_IMAGE_SIZE = 70 * SCALE;
    static final double BACKGROUND_BLUR = 50 * SCALE;
    static final double FOG_VELOCITY = 5 * SCALE;
    static final double LOGO_WIDTH = 700 * SCALE;
    static final double LOGO_HEIGHT = 425 * SCALE;
    static final double DEFAULT_SPACING = 10 * SCALE;
    static final double LOGIN_BOX_SIZE = 800 * SCALE;
    static final int FOG_CIRCLE_RADIUS = (int) (100 * SCALE);
    static final double DUELYST_LOGO_WIDTH = 500 * SCALE;
    static final double DUELYST_LOGO_HEIGHT = 108 * SCALE;
    static final double SCENE_WIDTH = 3636 * SCALE;
    static final double SCENE_HEIGHT = 2000 * SCALE;
    static final double FOREGROUND_WIDTH = 2400 * SCALE;
    static final double FOREGROUND_HEIGHT = 726 * SCALE;
    static final double NEAR_PILLARS_WIDTH = 2000 * SCALE;
    static final double NEAR_PILLARS_HEIGHT = 1700 * SCALE;
    static final double FAR_PILLARS_WIDTH = 1772 * SCALE;
    static final double FAR_PILLARS_HEIGHT = 1342 * SCALE;
    static final double VIGNETTE_WIDTH = 1429 * SCALE;
    static final double VIGNETTE_HEIGHT = 1400 * SCALE;
    static final Insets LOGIN_BOX_PADDING = new Insets(60 * SCALE, 20 * SCALE, 20 * SCALE, 20 * SCALE);
    static final Effect MENU_ITEM_SHADOW = new DropShadow(20* SCALE, Color.WHITE);
    static final Font DEFAULT_FONT = Font.font("SansSerif", FontWeight.BOLD, 25 * SCALE);
    static final Font LOGO_TEXT_FONT = Font.font("SansSerif", FontWeight.EXTRA_BOLD, 40 * SCALE);
    static final Font MENU_HINT_FONT = Font.font("SansSerif", FontWeight.BOLD, 25 * SCALE);
    static final Font MENU_ITEM_FONT = Font.font("DejaVu Sans Light", FontWeight.EXTRA_LIGHT, 55 * SCALE);
}
