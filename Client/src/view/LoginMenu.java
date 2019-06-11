package view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.gui.BackgroundMaker;
import models.gui.DefaultContainer;
import models.gui.LoginMenuContainer;
import models.gui.UIConstants;

import java.io.FileNotFoundException;

public class LoginMenu extends Show {
    private static LoginMenu menu;

    private LoginMenu() throws FileNotFoundException {
        root.setBackground(UIConstants.ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getMenuBackground();
        DefaultContainer container = new DefaultContainer(new LoginMenuContainer());

        AnchorPane sceneContents = new AnchorPane(background, container);

        root.getChildren().addAll(sceneContents);
    }

    @Override
    public void show() {
        super.show();
        BackgroundMaker.makeMenuBackgroundBlur();
    }

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
}