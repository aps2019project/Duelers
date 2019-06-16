package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.gui.UIConstants;
import view.LoginMenu;

public class GraphicalUserInterface {
    private static GraphicalUserInterface GUI;
    private Stage stage;
    private Scene scene;

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
