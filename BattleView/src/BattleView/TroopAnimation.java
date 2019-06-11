package BattleView;

import com.google.gson.Gson;
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
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TroopAnimation extends Transition {
    private final double[][] cellsX;
    private final double[][] cellsY;

    private final FramePosition[] attackFramePositions;
    private final FramePosition[] breathingFramePositions;
    private final FramePosition[] deathFramePositions;
    private final FramePosition[] hitFramePositions;
    private final FramePosition[] idleFramePositions;
    private final FramePosition[] runFramePositions;

    private final ImageView imageView;
    private final int frameWidth, frameHeight;
    private final double extraX, extraY;

    private boolean flag = false;
    private int nextIndex;
    private ACTION action;
    private FramePosition[] currentFramePositions;
    private int currentI, currentJ;

    private Group mapGroup;

    public TroopAnimation(Group mapGroup, double[][] cellsX, double[][] cellsY, String fileName, int j, int i) throws Exception {
        System.out.println(fileName);
        this.mapGroup = mapGroup;
        //file settings
        Playlist playlist = new Gson().fromJson(new FileReader("resources/troopAnimations/" + fileName + ".plist.json"), Playlist.class);
        attackFramePositions = playlist.getAttackFrames();
        breathingFramePositions = playlist.getBreathingFrames();
        deathFramePositions = playlist.getDeathFrames();
        hitFramePositions = playlist.getHitFrames();
        idleFramePositions = playlist.getIdleFrames();
        runFramePositions = playlist.getRunFrames();
        frameWidth = playlist.frameWidth;
        frameHeight = playlist.frameHeight;
        setCycleDuration(Duration.millis(playlist.frameDuration));
        extraX = playlist.extraX;
        extraY = playlist.extraY;

        this.cellsX = cellsX;
        this.cellsY = cellsY;

        currentI = i;
        currentJ = j;

        Image image = new Image(new FileInputStream("resources/troopAnimations/" + fileName + ".png"));
        imageView = new ImageView(image);
        imageView.setScaleX(Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setScaleY(Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setX(cellsX[j][i] - extraX);
        imageView.setY(cellsY[j][i] - extraY);
        mapGroup.getChildren().add(imageView);

        this.setCycleCount(INDEFINITE);
        setAction(ACTION.BREATHING);
    }

    @Override
    protected void interpolate(double v) {
        if (!flag && v < 0.5)
            flag = true;
        if (flag && v > 0.5) {
            imageView.setViewport(new Rectangle2D(currentFramePositions[nextIndex].x,
                    currentFramePositions[nextIndex].y, frameWidth, frameHeight));
            generateNextState();
            flag = false;
        }
    }

    private void generateNextState() {
        //has reached to destination
        if (action == ACTION.RUN
                && Math.abs(imageView.getX() - cellsX[currentJ][currentI] + extraX) < 2
                && Math.abs(imageView.getY() - cellsY[currentJ][currentI] + extraY) < 2) {//may bug
            setAction(ACTION.BREATHING);
            return;
        }
        if (nextIndex != (currentFramePositions.length - 1)) {
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
                mapGroup.getChildren().remove(imageView);
                break;
        }
    }

    private void setAction(ACTION action) {
        this.action = action;
        nextIndex = 0;
        this.stop();
        switch (action) {
            case BREATHING:
                currentFramePositions = breathingFramePositions;
                break;
            case DEATH:
                currentFramePositions = deathFramePositions;
                break;
            case ATTACK:
                currentFramePositions = attackFramePositions;
                break;
            case IDLE:
                currentFramePositions = idleFramePositions;
                break;
            case RUN:
                currentFramePositions = runFramePositions;
                break;
            case HIT:
                currentFramePositions = hitFramePositions;
                break;
        }
        this.play();
    }

    public void breathe() {
        setAction(ACTION.BREATHING);
    }

    public void kill() {
        setAction(ACTION.DEATH);
    }

    public void attack() {
        setAction(ACTION.ATTACK);
    }

    public void idle() {
        setAction(ACTION.IDLE);
    }

    public void hit() {
        setAction(ACTION.HIT);
    }

    public void moveTo(int j, int i) {
        setAction(ACTION.RUN);
        KeyValue xValue = new KeyValue(imageView.xProperty(), cellsX[j][i] - extraX);
        KeyValue yValue = new KeyValue(imageView.yProperty(), cellsY[j][i] - extraY);
        KeyFrame keyFrame = new KeyFrame(Duration.millis((Math.abs(currentI - i)
                + Math.abs(currentJ - j)) * Constants.MOVE_TIME_PER_CELL), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
        currentJ = j;
        currentI = i;
    }
}

enum ACTION {
    ATTACK, BREATHING, DEATH, HIT, IDLE, RUN
}

class Playlist {
    int frameWidth;
    int frameHeight;
    int frameDuration;
    double extraX;
    double extraY;
    private HashMap<String, ArrayList<FramePosition>> lists = new HashMap<>();

    FramePosition[] getHitFrames() {
        return lists.get("hit").toArray(new FramePosition[1]);
    }

    FramePosition[] getDeathFrames() {
        return lists.get("death").toArray(new FramePosition[1]);
    }

    FramePosition[] getBreathingFrames() {
        return lists.get("breathing").toArray(new FramePosition[1]);
    }

    FramePosition[] getIdleFrames() {
        return lists.get("idle").toArray(new FramePosition[1]);
    }

    FramePosition[] getAttackFrames() {
        return lists.get("attack").toArray(new FramePosition[1]);
    }

    FramePosition[] getRunFrames() {
        return lists.get("run").toArray(new FramePosition[1]);
    }
}

class FramePosition {
    final double x;
    final double y;

    FramePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

