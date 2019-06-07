package models;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

class Fog {
    private static final int NUMBER_OF_CIRCLES = 70;
    private static final long DURATION = (long) (130 * Math.pow(10, 9));
    private final double width;
    private final double height;
    private final Pane fog;
    private final Random random = new Random();

    Fog(double width, double height) {
        this.width = width;
        this.height = height;
        this.fog = new Pane();
        fog.setEffect(new GaussianBlur((width + height)));
        for (int i = 0; i < NUMBER_OF_CIRCLES; i++) {
            fog.getChildren().add(createFogElement());
        }
        moveFog();
    }

    private void moveFog() {
        AnimationTimer anim = new AnimationTimer() {

            private final double velocity = Constants.FOG_VELOCITY;
            private double currentX = Constants.SCENE_WIDTH;
            private double currentY = Constants.SCENE_HEIGHT - Constants.FOREGROUND_HEIGHT * 0.75;
            private long lastTimeStarted;

            @Override
            public void handle(long now) {
                if (now - lastTimeStarted > DURATION) {
                    lastTimeStarted = now;
                    currentX = Constants.SCENE_WIDTH;
                } else {
                    currentX -= velocity;
                }
                fog.relocate(currentX, currentY);
            }
        };

        anim.start();
    }

    private Circle createFogElement() {
        double centerX = Constants.FOG_CIRCLE_RADIUS + random.nextInt((int) (width - Constants.FOG_CIRCLE_RADIUS * 2));
        double centerY = Constants.FOG_CIRCLE_RADIUS + random.nextInt((int) (height - Constants.FOG_CIRCLE_RADIUS * 2));
        int radius = Constants.FOG_CIRCLE_RADIUS * 4 / 5 + random.nextInt(Constants.FOG_CIRCLE_RADIUS / 2);
        Circle circle = new Circle(centerX, centerY, radius);

        int shade = 200 + random.nextInt(50);
        double opacity = 1 - (Math.pow(centerX - width / 2, 2) + Math.pow(centerY - height / 2, 2)) / (Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
        circle.setFill(Color.rgb(shade, shade, shade, opacity));
        circle.setEffect(new ColorAdjust(1, 0.2, -0.4, 0));
        return circle;
    }

    Node getView() {
        return fog;
    }
}