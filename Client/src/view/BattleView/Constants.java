package view.BattleView;

import javafx.stage.Screen;

public class Constants {
    static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    static final double SCALE = SCREEN_WIDTH / 1920;
    static final double MAP_X = SCREEN_WIDTH * 0.2, MAP_Y = SCREEN_HEIGHT * 0.3, MAP_DOWNER_WIDTH = SCREEN_WIDTH * 0.56,
            MAP_UPPER_WIDTH = SCREEN_WIDTH * 0.42, MAP_HEIGHT = (MAP_DOWNER_WIDTH + MAP_UPPER_WIDTH) * 5 / 16;
    static final double TROOP_SCALE = 1.7;
    static final double CELLS_OPACITY = 0.4;
    static final double SPACE_BETWEEN_CELLS = 2;
    static final double MOVE_TIME_PER_CELL = 200;
}