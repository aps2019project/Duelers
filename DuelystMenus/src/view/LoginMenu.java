package view;

import controller.Controller;
import models.BackgroundMaker;
import models.Constants;
import models.DefaultContainer;
import models.LoginMenuContainer;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.io.FileNotFoundException;

public class LoginMenu {
    private static LoginMenu menu;
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    public static LoginMenu getInstance() {
        if (menu == null) {
            try {
                menu = new LoginMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    private LoginMenu() throws FileNotFoundException {
        root.setBackground(Constants.ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getMenuBackground();
        BackgroundMaker.makeMenuBackgroundBlur();
        DefaultContainer container = new DefaultContainer(new LoginMenuContainer());

        root.getChildren().addAll(background, container);
    }

    public void show() {
        Controller.setScene(scene);
    }
}