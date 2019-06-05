import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

final class Vignette extends Parent {
    private static final double OPACITY = 0.7;
    private Rectangle region;

    Vignette(double width, double height) {
        region = new Rectangle(width, height);

        applyChanges();

        getChildren().add(region);
    }

    private void applyChanges() {
        region.setFill(new LinearGradient(0, 0, 0, region.getHeight() * 0.95,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(1, Color.color(0, 0, 0, OPACITY))
        ));


    }
}

