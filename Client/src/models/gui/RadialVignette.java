package models.gui;

import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

public class RadialVignette extends Rectangle {
    public RadialVignette(double width, double height) {
        super(width, height);
        setFill(new RadialGradient(360, 0, width * 0.5, 0, height * 1.35,
                        false, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.TRANSPARENT),
                        new Stop(0.35, Color.TRANSPARENT),
                        new Stop(0.85, Color.BLACK),
                        new Stop(1, Color.BLACK)
                )
        );
    }
}
