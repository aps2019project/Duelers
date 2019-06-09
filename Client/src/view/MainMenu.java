package view;

import controller.GraphicalUserInterface;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.gui.BackgroundMaker;
import models.gui.MainMenuBox;
import models.gui.UIConstants;

import java.io.FileNotFoundException;

public class MainMenu {
    private static MainMenu menu;
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

    private MainMenu() throws FileNotFoundException {
        root.setBackground(UIConstants.ROOT_BACKGROUND);

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
        GraphicalUserInterface.getInstance().setScene(scene);
    }
}
