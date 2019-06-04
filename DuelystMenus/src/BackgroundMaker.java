import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileNotFoundException;

class BackgroundMaker {

    private static final String BACKGROUND_URL = "resources/menu/background/background.jpg";
    private static final String FOREGROUND_URL = "resources/menu/background/foreground.png";
    private static final String FAR_PILLAR_URL = "resources/menu/background/far_pillars.png";
    private static final String NEAR_PILLAR_URL = "resources/menu/background/near_pillars.png";
    private static final String VIGNETTE_URL = "resources/menu/background/vignette.png";

    static BorderPane makeMenuBackground() throws FileNotFoundException {
        BorderPane background = new BorderPane();

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

        background.getChildren().addAll(backgroundView, farPillarsView, nearPillarsView, fog.getView(), foregroundView, vignetteView);
        return background;
    }
}
