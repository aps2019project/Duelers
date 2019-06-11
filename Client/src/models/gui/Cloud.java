package models.gui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.FileNotFoundException;

class Cloud extends Pane {
    private static final Duration DURATION = Duration.seconds(90);
    private TranslateTransition transition;

    Cloud() throws FileNotFoundException {
        for (int i = 0; i < 3; i++) {
            getChildren().add(ImageLoader.loadImage(
                    "resources/particles/cloud_" + i + ".png",
                    UIConstants.CLOUD_WIDTH, UIConstants.CLOUD_HEIGHT, UIConstants.CLOUD_DISTANCE * i, 0
            ));
        }
        moveFog();
    }

    private void moveFog() {
        relocate(UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT - UIConstants.CLOUD_HEIGHT * 0.8);
        setOpacity(0.95);
        setEffect(new ColorAdjust(1, 0.9, -0.7, 0.6));
        transition = new TranslateTransition(DURATION, this);
        transition.setByX(-UIConstants.SCENE_WIDTH * 2);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(Animation.INDEFINITE);

    }

    void play() {
        transition.play();
    }

    void pause() {
        transition.pause();
    }
}