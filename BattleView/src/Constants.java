import javafx.stage.Screen;

public class Constants {
    static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    static final double MAP_X = SCREEN_WIDTH * 0.2, MAP_Y = SCREEN_HEIGHT * 0.3, MAP_DOWNER_WIDTH = SCREEN_WIDTH * 0.56,
            MAP_UPPER_WIDTH = SCREEN_WIDTH * 0.42, MAP_HEIGHT = (MAP_DOWNER_WIDTH + MAP_UPPER_WIDTH) * 5 / 16;
}
