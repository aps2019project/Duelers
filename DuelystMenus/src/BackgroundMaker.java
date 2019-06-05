import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

class BackgroundMaker {
    private static final String BACKGROUND_URL = "resources/menu/background/background.jpg";
    private static final String FOREGROUND_URL = "resources/menu/background/foreground.png";
    private static final String FAR_PILLAR_URL = "resources/menu/background/far_pillars.png";
    private static final String NEAR_PILLAR_URL = "resources/menu/background/near_pillars.png";
    private static final String VIGNETTE_URL = "resources/menu/background/vignette.png";
    private static final String PLAY_BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static BorderPane menuBackground;
    private static BorderPane playBackground;

    private static void makeMenuBackground() throws FileNotFoundException {
        menuBackground = new BorderPane();

        ImageView backgroundView = ImageLoader.loadImage(BACKGROUND_URL, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        ImageView foregroundView = ImageLoader.loadImage(
                FOREGROUND_URL, Constants.FOREGROUND_WIDTH, Constants.FOREGROUND_HEIGHT,
                Constants.SCENE_WIDTH - Constants.FOREGROUND_WIDTH,
                Constants.SCENE_HEIGHT - Constants.FOREGROUND_HEIGHT
        );
        ImageView farPillarsView = ImageLoader.loadImage(
                FAR_PILLAR_URL, Constants.FAR_PILLARS_WIDTH, Constants.FAR_PILLARS_HEIGHT,
                0, Constants.SCENE_HEIGHT - Constants.FAR_PILLARS_HEIGHT
        );
        ImageView nearPillarsView = ImageLoader.loadImage(
                NEAR_PILLAR_URL, Constants.NEAR_PILLARS_WIDTH, Constants.NEAR_PILLARS_HEIGHT,
                0, Constants.SCENE_HEIGHT - Constants.NEAR_PILLARS_HEIGHT
        );
        ImageView vignetteView = ImageLoader.loadImage(VIGNETTE_URL, Constants.VIGNETTE_WIDTH, Constants.VIGNETTE_HEIGHT);

        Fog fog = new Fog(Constants.SCENE_WIDTH * 0.5, Constants.FOREGROUND_HEIGHT * 0.9);

        menuBackground.getChildren().addAll(backgroundView, farPillarsView, nearPillarsView, fog.getView(), foregroundView, vignetteView);
    }

    private static void makePlayBackground() throws FileNotFoundException {
        playBackground = new BorderPane();

        ImageView backgroundView = ImageLoader.loadImage(PLAY_BACKGROUND_URL, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        backgroundView.setEffect(new GaussianBlur(Constants.BACKGROUND_BLUR));
        playBackground.setEffect(new ColorAdjust(0, 0, -0.6, -0.8));

        playBackground.getChildren().addAll(backgroundView);
    }

    static BorderPane getPlayBackground() throws FileNotFoundException {
        if (playBackground == null) {
            makePlayBackground();
        }
        return playBackground;
    }

    static BorderPane getMenuBackground() throws FileNotFoundException {
        if (menuBackground == null) {
            makeMenuBackground();
        }
        menuBackground.setEffect(null);
        return menuBackground;
    }

    static void makeMenuBackgroundBlur() {
        menuBackground.setEffect(new GaussianBlur(Constants.BACKGROUND_BLUR));
    }
}
