package view;

import javafx.scene.layout.BorderPane;
import models.gui.BackgroundMaker;
import models.gui.MainMenuBox;
import models.gui.UIConstants;

import java.io.FileNotFoundException;

public class MainMenu extends Show {
    private static MainMenu menu;

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
}
