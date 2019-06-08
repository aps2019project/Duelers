package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.BackgroundMaker;
import models.Constants;
import models.MainMenuBox;

import java.io.FileNotFoundException;

public class MainMenu {
    private static MainMenu menu;
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    private MainMenu() throws FileNotFoundException {
        root.setBackground(Constants.ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getMenuBackground();
        MainMenuBox menuBox = new MainMenuBox();

        root.getChildren().addAll(background, menuBox);
    }

    public static MainMenu getInstance() {
        if (menu == null) {
            try {
                menu = new MainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    public void show() {
        Controller.setScene(scene);
    }
}
