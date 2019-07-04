package view.BattleView;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class Constants {
    static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    static final double SCALE = SCREEN_WIDTH / 1920;
    //MAP
    static final double MAP_X = SCREEN_WIDTH * 0.2, MAP_Y = SCREEN_HEIGHT * 0.3;
    static final double MAP_DOWNER_WIDTH = SCREEN_WIDTH * 0.56, MAP_UPPER_WIDTH = SCREEN_WIDTH * 0.42;
    static final double MAP_HEIGHT = (MAP_DOWNER_WIDTH + MAP_UPPER_WIDTH) * 5 / 16;
    static final double HAND_X = SCREEN_WIDTH * 0.1;
    static final double HAND_Y = SCREEN_HEIGHT * 0.8;
    static final double TROOP_SCALE = 1.7;
    static final double CELLS_DEFAULT_OPACITY = 0.4;
    static final double SPACE_BETWEEN_CELLS = 2;
    static final double MOVE_TIME_PER_CELL = 200;
    static final Font AP_FONT = Font.font("SansSerif", FontWeight.LIGHT, 20 * SCALE);
    static final Font FLAG_FONT = Font.font("SansSerif", FontWeight.LIGHT, 15 * SCALE);

    static final Color SpellColor = Color.DEEPPINK;
    static final Color MoveColor = Color.GREEN;
    static final Color SelectedColor = Color.DARKGREEN;
    static final Color CanSelectColor = Color.GREEN;
    static final Color attackColor = Color.RED;
    //TODO:Death Color
    static final Color defaultColor = Color.DARKBLUE;
}