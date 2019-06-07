package models;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class DefaultContainer extends BorderPane {

    public DefaultContainer(Node center) {
        setMinSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        setCenter(center);
    }
}
