package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.gui.UIConstants;
import view.LoginMenu;

public class GraphicalUserInterface {
    private static GraphicalUserInterface GUI;
    private Stage stage;
    private Scene scene;
    private MediaPlayer backgroundMusicPlayer;

    public static GraphicalUserInterface getInstance() {
        if (GUI == null) {
            GUI = new GraphicalUserInterface();
        }
        return GUI;
    }

    private GraphicalUserInterface() {
    }

    public void start(Stage stage) {
        this.stage = stage;
        new LoginMenu().show();
        setStageProperties(stage);
    }

    public void changeScene(AnchorPane root) {
        if (scene == null) {
            makeScene(root);
        } else {
            scene.setRoot(root);
        }
    }

    public void setBackgroundMusic(Media media) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
        backgroundMusicPlayer = new MediaPlayer(media);
        backgroundMusicPlayer.setCycleCount(-1);
        backgroundMusicPlayer.setAutoPlay(true);
    }

    private void setStageProperties(Stage stage) {
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("DUELYST");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> Client.getInstance().close());
    }

    private void makeScene(AnchorPane root) {
        scene = new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
        scene.getStylesheets().add(this.getClass().getResource("/styles/scroll_pane.css").toExternalForm());
        scene.setCursor(UIConstants.DEFAULT_CURSOR);
        stage.setScene(scene);
    }
}
