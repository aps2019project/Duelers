package models;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

class MenuLabel extends Label {
    MenuLabel(String title) {
        super(title);
        setFont(Constants.MENU_ITEM_FONT);
        setTextFill(Color.WHITE);
    }
}
