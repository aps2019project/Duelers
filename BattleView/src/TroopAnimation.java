import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;

public class TroopAnimation extends Transition {
    private final double[][] cellsX;
    private final double[][] cellsY;

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
    private int currentI, currentJ;


    public TroopAnimation(Group root, double[][] cellsX, double[][] cellsY, String fileName, int j, int i) throws Exception {
        //Read From Settings********************
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
        //*******************************
        this.cellsX = cellsX;
        this.cellsY = cellsY;

        currentI = i;
        currentJ = j;

        Image image = new Image(new FileInputStream(fileName + ".png"));
        imageView = new ImageView(image);
        imageView.setScaleX(Constants.SCREEN_WIDTH / 1920);
        imageView.setScaleY(Constants.SCREEN_HEIGHT / 1080);
        imageView.setX(cellsX[j][i] - EXTRA_X);
        imageView.setY(cellsY[j][i] - EXTRA_Y);
        root.getChildren().add(imageView);

        setCycleDuration(Duration.millis(FRAME_DURATION));
        this.setCycleCount(INDEFINITE);

        setAction(ACTION.BREATHING);
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
        //has reached to destination
        if (action == ACTION.RUN && imageView.getX() == cellsX[currentJ][currentI] - EXTRA_X && imageView.getY() == cellsY[currentJ][currentI] - EXTRA_Y) {//may bug
            setAction(ACTION.BREATHING);
            return;
        }
        if (nextIndex != (currentActionX.length - 1)) {
            nextIndex++;
            return;
        }
        //has reached to last frame
        switch (action) {
            case HIT:
            case ATTACK:
                setAction(ACTION.BREATHING);
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

    public void moveTo(int j, int i) {
        this.action = ACTION.RUN;
        nextIndex = 0;
        this.stop();
        currentActionX = RUN_X_COORDINATE;
        currentActionY = RUN_Y_COORDINATE;
        this.play();
        KeyValue xValue = new KeyValue(imageView.xProperty(), cellsX[j][i] - EXTRA_X);
        KeyValue yValue = new KeyValue(imageView.yProperty(), cellsY[j][i] - EXTRA_Y);
        KeyFrame keyFrame = new KeyFrame(Duration.millis((Math.abs(currentI - i) + Math.abs(currentJ - j)) * 200), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
        currentJ = j;
        currentI = i;
    }
}
