package view.BattleView;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.gui.DefaultLabel;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TroopAnimation extends Transition {
    private final boolean isPlayer1Troop;
    private final boolean isMyTroop;
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

    private boolean frameShowFlag = false;
    private int nextIndex;
    private ACTION action;
    private FramePosition[] currentFramePositions;
    private int currentI, currentJ;

    private DefaultLabel apLabel;
    private DefaultLabel hpLabel;
    private ImageView apImage;
    private ImageView hpImage;

    private Group mapGroup;
    private Group troopGroup;

    TroopAnimation(Group mapGroup, double[][] cellsX, double[][] cellsY, String fileName, int j, int i, boolean isPlayer1Troop, boolean isMyTroop) throws Exception {
        this.mapGroup = mapGroup;
        this.isPlayer1Troop = isPlayer1Troop;
        this.isMyTroop = isMyTroop;

        //read settings
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

        this.cellsX = cellsX;
        this.cellsY = cellsY;

        currentI = i;
        currentJ = j;

        Image image = new Image(new FileInputStream("resources/troopAnimations/" + fileName + ".png"));
        imageView = new ImageView(image);
        if (isPlayer1Troop)
            imageView.setScaleX(1);
        else
            imageView.setScaleX(-1);
        imageView.setFitWidth(frameWidth * Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setFitHeight(frameHeight * Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setX(-playlist.extraX * Constants.SCALE);
        imageView.setY(-playlist.extraY * Constants.SCALE);
        imageView.setViewport(new Rectangle2D(0, 0, 1, 1));

        troopGroup = new Group();
        troopGroup.setLayoutX(cellsX[j][i]);
        troopGroup.setLayoutY(cellsY[j][i]);
        troopGroup.getChildren().add(imageView);

        this.setCycleCount(INDEFINITE);

        action = ACTION.IDLE;
        setAction(ACTION.BREATHING);

        addApHp();

        mapGroup.getChildren().add(troopGroup);
    }

    private void addApHp() throws Exception {
        apLabel = new DefaultLabel("", Constants.AP_FONT, Color.WHITE, -Constants.SCALE * 29, Constants.SCALE * 15);
        hpLabel = new DefaultLabel("", Constants.AP_FONT, Color.WHITE, Constants.SCALE * 14, Constants.SCALE * 15);
        if (isMyTroop) {
            apImage = new ImageView(new Image(new FileInputStream("resources/ui/icon_atk@2x.png")));
            hpImage = new ImageView(new Image(new FileInputStream("resources/ui/icon_hp@2x.png")));
        } else {
            apImage = new ImageView(new Image(new FileInputStream("resources/ui/icon_atk_bw@2x.png")));
            hpImage = new ImageView(new Image(new FileInputStream("resources/ui/icon_hp_bw@2x.png")));
        }
        apImage.setFitHeight(apImage.getImage().getHeight() * Constants.SCALE * 0.4);
        apImage.setFitWidth(apImage.getImage().getWidth() * Constants.SCALE * 0.4);
        apImage.setX(-Constants.SCALE * 50);
        apImage.setY(Constants.SCALE * 0);
        hpImage.setFitHeight(hpImage.getImage().getHeight() * Constants.SCALE * 0.4);
        hpImage.setFitWidth(hpImage.getImage().getWidth() * Constants.SCALE * 0.4);
        hpImage.setX(-Constants.SCALE * 6);
        hpImage.setY(Constants.SCALE * 0);
        troopGroup.getChildren().addAll(apImage, hpImage, apLabel, hpLabel);
    }

    void updateApHp(int ap,int hp){
        apLabel.setText(Integer.toString(ap));
        hpLabel.setText(Integer.toString(hp));
    }

    @Override
    protected void interpolate(double v) {
        if (!frameShowFlag && v < 0.5)
            frameShowFlag = true;
        if (frameShowFlag && v > 0.5) {
            imageView.setViewport(new Rectangle2D(currentFramePositions[nextIndex].x,
                    currentFramePositions[nextIndex].y, frameWidth, frameHeight));
            generateNextState();
            frameShowFlag = false;
        }
    }

    private void generateNextState() {
        //has reached to destination
        if (action == ACTION.RUN
                && Math.abs(troopGroup.getLayoutX() - cellsX[currentJ][currentI]) < 2
                && Math.abs(troopGroup.getLayoutY() - cellsY[currentJ][currentI]) < 2) {//may bug
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
                mapGroup.getChildren().remove(troopGroup);
                break;
        }
    }

    private void setAction(ACTION action) {
        this.action = action;
        nextIndex = 0;
        this.stop();
        switch (action) {
            case BREATHING:
                if (isPlayer1Troop)
                    imageView.setScaleX(1);
                else
                    imageView.setScaleX(-1);
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

    public void kill() {
        setAction(ACTION.DEATH);
    }

    public void attack(int i) {
        if (i > currentI)
            imageView.setScaleX(1);
        if (i < currentI)
            imageView.setScaleX(-1);
        setAction(ACTION.ATTACK);
    }

    public void hit(int i) {
        if (i > currentI)
            imageView.setScaleX(1);
        if (i < currentI)
            imageView.setScaleX(-1);
        setAction(ACTION.HIT);
    }

    public void moveTo(int j, int i) {
        if (i > currentI)
            imageView.setScaleX(1);
        if (i < currentI)
            imageView.setScaleX(-1);
        setAction(ACTION.RUN);
        KeyValue xValue = new KeyValue(troopGroup.layoutXProperty(), cellsX[j][i]);
        KeyValue yValue = new KeyValue(troopGroup.layoutYProperty(), cellsY[j][i]);
        KeyFrame keyFrame = new KeyFrame(Duration.millis((Math.abs(currentI - i)
                + Math.abs(currentJ - j)) * Constants.MOVE_TIME_PER_CELL), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

        currentJ = j;
        currentI = i;
    }

    enum ACTION {
        ATTACK, BREATHING, DEATH, HIT, IDLE, RUN, STOPPED
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

    public int getColumn() {
        return currentI;
    }

    public int getRow() {
        return currentJ;
    }

    public Group getTroopGroup() {
        return troopGroup;
    }

    void select(){
        if (action == ACTION.BREATHING)
            setAction(ACTION.IDLE);
    }

    public void diSelect() {
        if (action == ACTION.IDLE)
            setAction(ACTION.BREATHING);
    }
}

