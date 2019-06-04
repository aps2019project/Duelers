import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class LoginMenu {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    void show() throws FileNotFoundException {
        Main.setScene(scene);

        BorderPane pane = new BorderPane();

        Image background = new Image(new FileInputStream("resources/menu/background/background.jpg"));
        ImageView backgroundView = new ImageView(background);
        backgroundView.resize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        Image foreground = new Image(new FileInputStream("resources/menu/background/foreground.png"));
        ImageView foregroundView = new ImageView(foreground);
        foregroundView.setFitWidth(Constants.FOREGROUND_WIDTH);
        foregroundView.setFitHeight(Constants.FOREGROUND_HEIGHT);
        foregroundView.relocate(
                Constants.SCENE_WIDTH - Constants.FOREGROUND_WIDTH,
                Constants.SCENE_HEIGHT - Constants.FOREGROUND_HEIGHT
        );

        Image farPillars = new Image(new FileInputStream("resources/menu/background/far_pillars.png"));
        ImageView farPillarsView = new ImageView(farPillars);
        farPillarsView.setFitWidth(Constants.FAR_PILLARS_WIDTH);
        farPillarsView.setFitHeight(Constants.FAR_PILLARS_HEIGHT);
        farPillarsView.relocate(0, Constants.SCENE_HEIGHT - Constants.FAR_PILLARS_HEIGHT);

        Image nearPillars = new Image(new FileInputStream("resources/menu/background/near_pillars.png"));
        ImageView nearPillarsView = new ImageView(nearPillars);
        nearPillarsView.setFitWidth(Constants.NEAR_PILLARS_WIDTH);
        nearPillarsView.setFitHeight(Constants.NEAR_PILLARS_HEIGHT);
        nearPillarsView.relocate(0, Constants.SCENE_HEIGHT - Constants.NEAR_PILLARS_HEIGHT);

        Image vignette = new Image(new FileInputStream("resources/menu/background/vignette.png"));
        ImageView vignetteView = new ImageView(vignette);
        vignetteView.setFitWidth(Constants.VIGNETTE_WIDTH);
        vignetteView.setFitHeight(Constants.VIGNETTE_HEIGHT);

        Fog fog = new Fog(Constants.SCENE_WIDTH * 0.5, Constants.FOREGROUND_HEIGHT * 0.75);

        pane.getChildren().addAll(backgroundView, farPillarsView, nearPillarsView, fog.getView(), foregroundView, vignetteView);
        root.getChildren().addAll(pane);
    }
}