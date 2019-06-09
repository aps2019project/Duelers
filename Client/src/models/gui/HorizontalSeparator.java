package models.gui;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

class HorizontalSeparator extends Separator {
    private static final double SEPARATOR_OPACITY = 0.3;

    HorizontalSeparator() {
        super(Orientation.HORIZONTAL);
        setOpacity(SEPARATOR_OPACITY);
    }
}
