package models.gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DefaultLabel extends Label {
    public DefaultLabel(String title, Font font, Color color) {
        super(title);
        setFont(font);
        setTextFill(color);
    }
}
