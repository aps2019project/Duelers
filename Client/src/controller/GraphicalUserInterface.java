package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import models.gui.UIConstants;
import view.LoginMenu;

public class GraphicalUserInterface {
    private static GraphicalUserInterface GUI;
    private Stage stage;

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
        this.stage.setTitle("DUELYST");
        this.stage.setResizable(false);
        this.stage.show();
        this.stage.setOnCloseRequest(event -> Client.getInstance().close());

        LoginMenu.getInstance().show();
    }

    public void setScene(Scene scene) {
        scene.setCursor(UIConstants.DEFAULT_CURSOR);
        stage.setScene(scene);
    }
}
