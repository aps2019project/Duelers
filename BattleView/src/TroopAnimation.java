import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;

public class TroopAnimation extends Transition {
    private final double[] ATTACK_X_COORDINATE;
    private final double[] ATTACK_Y_COORDINATE;
    private final double[] BREATHING_X_COORDINATE;
    private final double[] BREATHING_Y_COORDINATE;
    private final double[] DEATH_X_COORDINATE;
    private final double[] DEATH_Y_COORDINATE;
    private final double[] HIT_X_COORDINATE;
    private final double[] HIT_Y_COORDINATE;
    private final double[] IDLE_X_COORDINATE;
    private final double[] IDLE_Y_COORDINATE;
    private final double[] RUN_X_COORDINATE;
    private final double[] RUN_Y_COORDINATE;

    private final ImageView imageView;
    private final int WIDTH, HEIGHT;
    private final int FRAME_DURATION;
    private final double EXTRA_X, EXTRA_Y;

    private boolean flag = false;
    private int nextIndex;
    private ACTION action;
    private double[] currentActionX;
    private double[] currentActionY;

    public TroopAnimation(Group root, String fileName, double x, double y) throws Exception {
        //Read From Settings
        ATTACK_X_COORDINATE = new double[]{404, 808, 707, 707, 707, 707, 707, 707, 707, 707, 707, 707, 606, 606,
                606, 606, 606, 606, 606, 606, 606};
        ATTACK_Y_COORDINATE = new double[]{0, 0, 909, 808, 707, 606, 505, 404, 303, 202, 101, 0, 909, 808, 707,
                606, 505, 404, 303, 202, 101};
        BREATHING_X_COORDINATE = new double[]{606, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 404, 404, 404};
        BREATHING_Y_COORDINATE = new double[]{0, 909, 808, 707, 606, 505, 404, 303, 202, 101, 0, 909, 808, 707};
        DEATH_X_COORDINATE = new double[]{404, 404, 404, 404, 404, 404, 808, 303, 303, 303, 303, 303, 303, 303, 303};
        DEATH_Y_COORDINATE = new double[]{606, 505, 404, 303, 202, 101, 101, 909, 808, 707, 606, 505, 404, 303, 404};
        HIT_X_COORDINATE = new double[]{303, 303, 303};
        HIT_Y_COORDINATE = new double[]{202, 101, 0};
        IDLE_X_COORDINATE = new double[]{202, 101, 0};
        IDLE_Y_COORDINATE = new double[]{808, 808, 808};
        RUN_X_COORDINATE = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
        RUN_Y_COORDINATE = new double[]{707, 606, 505, 404, 303, 202, 101, 0};

        WIDTH = 100;
        HEIGHT = 100;
        FRAME_DURATION = 100;
        EXTRA_X = 50;
        EXTRA_Y = 60;

        action = ACTION.IDLE;
        currentActionX = IDLE_X_COORDINATE;
        currentActionY = IDLE_Y_COORDINATE;
        nextIndex = 0;


        Image image = new Image(new FileInputStream(fileName + ".png"));
        imageView = new ImageView(image);
        imageView.setScaleX(1);
        imageView.setScaleY(1);
        imageView.setX(x - EXTRA_X);
        imageView.setY(y - EXTRA_Y);

        root.getChildren().add(imageView);

        setCycleDuration(Duration.millis(FRAME_DURATION));
        this.setCycleCount(INDEFINITE);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        if (v < 0.5 && !flag)
            flag = true;
        if (v > 0.5 && flag) {
            imageView.setViewport(new Rectangle2D(currentActionX[nextIndex], currentActionY[nextIndex], WIDTH, HEIGHT));
            generateNextState();
            flag = false;
        }
    }

    private void generateNextState() {
        if (nextIndex != (currentActionX.length - 1)) {
            nextIndex++;
            return;
        }
        switch (action) {
            case HIT:
            case ATTACK:
                action = ACTION.IDLE;
                currentActionX = IDLE_X_COORDINATE;
                currentActionY = IDLE_Y_COORDINATE;
                nextIndex = 0;
                break;
            case RUN:
            case IDLE:
            case BREATHING:
                nextIndex = 0;
                break;
            case DEATH:
                //do nothing
                break;
        }
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
        nextIndex = 0;
        this.stop();
        switch (action) {
            case BREATHING:
                currentActionX = BREATHING_X_COORDINATE;
                currentActionY = BREATHING_Y_COORDINATE;
                break;
            case DEATH:
                currentActionX = DEATH_X_COORDINATE;
                currentActionY = DEATH_Y_COORDINATE;
                break;
            case ATTACK:
                currentActionX = ATTACK_X_COORDINATE;
                currentActionY = ATTACK_Y_COORDINATE;
                break;
            case IDLE:
                currentActionX = IDLE_X_COORDINATE;
                currentActionY = IDLE_Y_COORDINATE;
                break;
            case RUN:
                currentActionX = RUN_X_COORDINATE;
                currentActionY = RUN_Y_COORDINATE;
                break;
            case HIT:
                currentActionX = HIT_X_COORDINATE;
                currentActionY = HIT_Y_COORDINATE;
                break;
        }
        this.play();
    }
}
