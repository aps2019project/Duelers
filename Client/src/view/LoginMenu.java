package view;

import controller.GraphicalUserInterface;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.gui.BackgroundMaker;
import models.gui.DefaultContainer;
import models.gui.LoginMenuContainer;
import models.gui.UIConstants;

import java.io.FileNotFoundException;

public class LoginMenu {
    private static LoginMenu menu;
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

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
        root.setBackground(UIConstants.ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getMenuBackground();
        BackgroundMaker.makeMenuBackgroundBlur();
        DefaultContainer container = new DefaultContainer(new LoginMenuContainer());

        root.getChildren().addAll(background, container);
    }

    public void show() {
        GraphicalUserInterface.getInstance().setScene(scene);
    }
}