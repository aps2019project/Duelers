import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

class Fog {
    private final double width;
    private final double height;
    private final Pane fog;
    private final Random random = new Random();

    Fog(double width, double height) {
        this.width = width;
        this.height = height;
        this.fog = new Pane();
        fog.setEffect(new GaussianBlur((width + height)));
        for (int i = 0; i < 250; i++) {
            fog.getChildren().add(createFogElement());
        }
        moveFog();
    }

    private void moveFog() {
        AnimationTimer anim = new AnimationTimer() {

            private final double velocity = 5;
            private final long duration = (long) (80 * Math.pow(10, 9));
            private double currentX = Constants.SCENE_WIDTH;
            private double currentY = Constants.SCENE_HEIGHT - Constants.FOREGROUND_HEIGHT * 0.75;
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
                Constants.FOG_CIRCLE_RADIUS + random.nextInt((int) (width - Constants.FOG_CIRCLE_RADIUS * 2)),
                Constants.FOG_CIRCLE_RADIUS + random.nextInt((int) (height - Constants.FOG_CIRCLE_RADIUS * 2)),
                radius);

        int shade = 200 + random.nextInt(50);
        double opacity = 0.2 + random.nextDouble() * 0.8;
        circle.setFill(Color.rgb(shade, shade, shade, opacity));
        circle.setEffect(new ColorAdjust(1, 0.2, -0.4, 0));
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