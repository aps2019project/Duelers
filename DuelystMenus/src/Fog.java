import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

class Fog {
    private final double width;
    private final double height;
    private final Pane fog;
    private final Random random = new Random();

    Fog(double width, double height) throws FileNotFoundException {
        this.width = width;
        this.height = height;
        this.fog = new Pane();
        fog.relocate(800, 0);
        fog.setEffect(new GaussianBlur((width + height) / 5));
        for (int i = 0; i < 100; i++) {
            fog.getChildren().add(createFogElement());
        }

        Image vignette = new Image(new FileInputStream("resources/menu/background/vignette.png"));
        ImageView vignetteView = new ImageView(vignette);
        vignetteView.setFitWidth(width * 2);
        vignetteView.setFitHeight(height * 2);
        fog.getChildren().add(vignetteView);

        moveFog();
    }

    private void moveFog() {
        AnimationTimer anim = new AnimationTimer() {

            private final double velocity = 5;
            private final long duration = (long) (80 * Math.pow(10, 9));
            private double currentX = Constants.SCENE_WIDTH;
            private double currentY = Constants.SCENE_HEIGHT - height;
            private long lastTimeStarted;
            private long lastTimeMoved;

            @Override
            public void handle(long now) {
                if (now - lastTimeStarted > duration) {
                    lastTimeStarted = now;
                    currentX = Constants.SCENE_WIDTH;
                } else {
                    if (now - lastTimeMoved > 1000) {
                        lastTimeMoved = now;
                        currentX -= velocity;
                    }
                }
                fog.relocate(currentX, currentY);
            }
        };

        anim.start();
    }

    private Circle createFogElement() {
        int radius = 15 + random.nextInt(100);
        Circle circle = new Circle(
                100 + random.nextInt((int) (width - 200)),
                100 + random.nextInt((int) (height - 200)),
                radius);

        int shade = 200 + random.nextInt(50);
        double opacity = 0.4 + random.nextDouble() * 0.6;
        circle.setFill(Color.rgb(shade, shade, shade, opacity));
        AnimationTimer anim = new AnimationTimer() {

            double xVel = random.nextDouble() * 40 - 20;
            double yVel = random.nextDouble() * 40 - 20;

            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double elapsedSeconds = (now - lastUpdate) / 1_000_000_000.0;
                    double x = circle.getCenterX();
                    double y = circle.getCenterY();
                    if (x + elapsedSeconds * xVel + radius > width || x + elapsedSeconds * xVel - radius < 0) {
                        xVel = -xVel;
                    }
                    if (y + elapsedSeconds * yVel + radius > height || y + elapsedSeconds * yVel - radius < 0) {
                        yVel = -yVel;
                    }
                    circle.setCenterX(x + elapsedSeconds * xVel);
                    circle.setCenterY(y + elapsedSeconds * yVel);
                }
                lastUpdate = now;
            }

        };
        anim.start();
        return circle;
    }

    Node getView() {
        return fog;
    }
}