package models;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Constants {
    public static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(44, 33, 129), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    public static final int FLAG_NUM_MIN = 5;
    public static final int FLAG_NUM_MAX = 30;
    public static final int FLAG_NUM_DEFAULT = 10;
    static final Effect PLAY_MENU_HOVER_EFFECT = new ColorAdjust(0, 0.15, 0.05, 0);
    static final Effect BACK_BUTTON_HOVER_EFFECT = new ColorAdjust(0, 0.15, 0.2, 0);
    private static final double SCALE = 0.5;
    public static final double DEFAULT_SPACING = 10 * SCALE;
    public static final double SCENE_WIDTH = 3636 * SCALE;
    public static final double SCENE_HEIGHT = 2000 * SCALE;
    static final double ON_DIALOG_BLUR = 30 * SCALE;
    static final double DIALOG_MAX_WIDTH = 1200 * SCALE;
    static final double DIALOG_MAX_HEIGHT = 700 * SCALE;
    static final double BACK_BUTTON_SIZE = 400 * SCALE;
    static final double PLAY_MENU_BUTTON_WIDTH = 632 * SCALE;
    static final double PLAY_MENU_BUTTON_HEIGHT = 1160 * SCALE;
    static final double PLAY_MENU_PLATE_HEIGHT = 85 * SCALE;
    static final double MAIN_MENU_BOX_X = 100 * SCALE;
    static final double MAIN_MENU_BOX_Y = 400 * SCALE;
    static final double MENU_HINT_WIDTH = 250 * SCALE;
    static final double MENU_ITEM_IMAGE_SIZE = 70 * SCALE;
    static final double BACKGROUND_BLUR = 50 * SCALE;
    static final double FOG_VELOCITY = 5 * SCALE;
    static final double LOGO_WIDTH = 700 * SCALE;
    static final double LOGO_HEIGHT = 425 * SCALE;
    static final double LOGIN_BOX_SIZE = 800 * SCALE;
    static final double DUELYST_LOGO_WIDTH = 500 * SCALE;
    static final double DUELYST_LOGO_HEIGHT = 108 * SCALE;
    static final double FOREGROUND_WIDTH = 2400 * SCALE;
    static final double FOREGROUND_HEIGHT = 726 * SCALE;
    static final double NEAR_PILLARS_WIDTH = 2000 * SCALE;
    static final double NEAR_PILLARS_HEIGHT = 1700 * SCALE;
    static final double FAR_PILLARS_WIDTH = 1772 * SCALE;
    static final double FAR_PILLARS_HEIGHT = 1342 * SCALE;
    static final double VIGNETTE_WIDTH = 1429 * SCALE;
    static final double VIGNETTE_HEIGHT = 1400 * SCALE;
    static final int FOG_CIRCLE_RADIUS = (int) (100 * SCALE);
    static final Insets LOGIN_BOX_PADDING = new Insets(60 * SCALE, 20 * SCALE, 20 * SCALE, 20 * SCALE);
    static final Effect WHITE_TEXT_SHADOW = new DropShadow(20 * SCALE, Color.WHITE);
    static final Effect PLAY_MENU_BOX_SHADOW = new DropShadow(40 * SCALE, Color.BLACK);
    static final Font DEFAULT_FONT = Font.font("SansSerif", FontWeight.BOLD, 25 * SCALE);
    static final Font DIALOG_TEXT_FONT = Font.font("SansSerif", FontWeight.EXTRA_BOLD, 35 * SCALE);
    static final Font LOGO_TEXT_FONT = Font.font("SansSerif", FontWeight.EXTRA_BOLD, 40 * SCALE);
    static final Font MENU_HINT_FONT = Font.font("SansSerif", FontWeight.BOLD, 25 * SCALE);
    static final Font MENU_ITEM_FONT = Font.font("DejaVu Sans Light", FontWeight.EXTRA_LIGHT, 55 * SCALE);
    static final Font PLAY_MENU_ITEM_FONT = Font.font("SansSerif", FontWeight.BOLD, 55 * SCALE);
    public static Cursor DEFAULT_CURSOR;
    static Cursor SELECT_CURSOR;
    static Image PLATE_IMAGE;

    static {
        try {
            DEFAULT_CURSOR = new ImageCursor(new Image(new FileInputStream("resources/cursors/default.png")));
            SELECT_CURSOR = new ImageCursor(new Image(new FileInputStream("resources/cursors/select.png")));
            PLATE_IMAGE = new Image(new FileInputStream("resources/menu/playButtons/panel_trim_plate.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
