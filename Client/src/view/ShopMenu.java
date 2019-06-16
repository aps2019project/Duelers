package view;

import controller.Client;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.gui.BackButton;
import models.gui.BackgroundMaker;
import models.gui.UIConstants;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

public class ShopMenu extends Show implements PropertyChangeListener {
    private static ShopMenu menu;

    ShopMenu() {
        menu = this;
        try {
            root.setBackground(UIConstants.ROOT_BACKGROUND);

            BorderPane background = BackgroundMaker.getMenuBackground();
            BackButton backButton = new BackButton(event -> new MainMenu().show());

            AnchorPane sceneContents = new AnchorPane(background, backButton);

            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        Client.getInstance().getAccount().addPropertyChangeListener(this);
        BackgroundMaker.makeMenuBackgroundFrozen();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getPropertyName().equals("mp")) {
//            Platform.runLater(() -> updateCollection((int)evt.getNewValue()));
//        }
    }
}
