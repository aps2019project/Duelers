package view;

import controller.GameResultController;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.gui.BackgroundMaker;
import models.gui.ImageLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static models.gui.UIConstants.SCENE_HEIGHT;
import static models.gui.UIConstants.SCENE_WIDTH;

public class GameResultMenu extends Show {
    private static final String backgroundUrl = "resources/backGrounds/battlemap6_middleground@2x.png";
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final Map<Boolean, StatusImages> status = new HashMap<>();
    private static final GameResultController CONTROLLER = GameResultController.getInstance();

    static {
        try {
            status.put(true, new StatusImages(
                    "scene_diamonds_background_victory@2x",
                    "scene_diamonds_middleground_victory@2x",
                    "scene_diamonds_foreground_friendly@2x"
            ));
            status.put(false, new StatusImages(
                    "scene_diamonds_background_defeat@2x",
                    "scene_diamonds_middleground_defeat@2x",
                    "scene_diamonds_foreground_enemy@2x"
            ));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GameResultMenu() {
        try {
            root.setBackground(ROOT_BACKGROUND);
            BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);

            ImageView resultBackground = ImageLoader.makeImageView(
                    status.get(CONTROLLER.amInWinner()).background, SCENE_WIDTH, SCENE_HEIGHT
            );

            AnchorPane sceneContents = new AnchorPane(background);
            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private static class StatusImages {
        private final Image background;
        private final Image middleground;
        private final Image foreground;

        private StatusImages(String backName, String middleName, String foreName) throws FileNotFoundException {
            background = new Image(new FileInputStream(getUrl(backName)));
            middleground = new Image(new FileInputStream(getUrl(middleName)));
            foreground = new Image(new FileInputStream(getUrl(foreName)));
        }

        private String getUrl(String name) {
            return "resources/result_menu/" + name + ".png";
        }
    }
}
