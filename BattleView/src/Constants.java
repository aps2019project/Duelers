import javafx.stage.Screen;

public class Constants {
    static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    static final double X = SCREEN_WIDTH * 0.2, Y = SCREEN_HEIGHT * 0.27, L1 = SCREEN_WIDTH * 0.57,
            L2 = SCREEN_WIDTH * 0.41, H = (L1 + L2) * 5 / 16;
}
