package view.BattleView;

import com.google.gson.Gson;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.ICard;
import models.gui.ImageLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardAnimation extends Transition {
    private static final Map<String, Image> cachedImages = new HashMap<>();
    private final Pane pane;
    private final FramePosition[] activeFramePositions;
    private final FramePosition[] inActiveFramePositions;

    private final ImageView imageView;
    private final int frameWidth, frameHeight;
    private final double extraX, extraY;

    private boolean flag = false;
    private int nextIndex;
    private ACTION action;
    private FramePosition[] currentFramePositions;

    public CardAnimation(Pane pane, ICard card, double y, double x) throws FileNotFoundException {
        this.pane = pane;
        //file settings
        Playlist playlist;
        Image image;
        switch (card.getType()) {
            case SPELL:
            case USABLE_ITEM:
            case COLLECTIBLE_ITEM:
                image = cachedImages.computeIfAbsent(card.getSpriteName(), key -> ImageLoader.load("resources/icons/" + card.getSpriteName() + ".png"));
                playlist = new Gson().fromJson(new FileReader("resources/icons/" + card.getSpriteName() + ".plist.json"), Playlist.class);
                activeFramePositions = playlist.getLists().get("active").toArray(new FramePosition[1]);
                inActiveFramePositions = playlist.getLists().get("inactive").toArray(new FramePosition[1]);
                extraX = 38 * Constants.SCALE;
                extraY = 31 * Constants.SCALE;
                break;
            default:
                image = cachedImages.computeIfAbsent(card.getSpriteName(), key -> ImageLoader.load("resources/troopAnimations/" + card.getSpriteName() + ".png"));
                playlist = new Gson().fromJson(new FileReader("resources/troopAnimations/" + card.getSpriteName() + ".plist.json"), Playlist.class);
                activeFramePositions = playlist.getLists().get("idle").toArray(new FramePosition[1]);
                inActiveFramePositions = activeFramePositions;
                extraX = playlist.extraX * Constants.SCALE;
                extraY = (playlist.extraY - 20) * Constants.SCALE;
        }

        frameWidth = playlist.frameWidth;
        frameHeight = playlist.frameHeight;
        setCycleDuration(Duration.millis(playlist.frameDuration));


        imageView = new ImageView(image);

        imageView.setFitWidth(frameWidth * Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setFitHeight(frameHeight * Constants.TROOP_SCALE * Constants.SCALE);
        imageView.setX(x - extraX);
        imageView.setY(y - extraY);

        imageView.setViewport(new Rectangle2D(0, 0, 1, 1));
        this.pane.getChildren().add(imageView);


        this.setCycleCount(INDEFINITE);
        setAction(ACTION.STOPPED);
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
        if (action == ACTION.STOPPED) {
            nextIndex = 0;
            return;
        }
        if (nextIndex != (currentFramePositions.length - 1)) {
            nextIndex++;
            return;
        }
        //has reached to last frame
        switch (action) {
            case ACTIVE:
                pane.getChildren().remove(imageView);
                break;
            case IN_ACTIVE:
                nextIndex = 0;
                break;
        }
    }

    private void setAction(ACTION action) {
        if (this.action == action)
            return;
        this.action = action;
        nextIndex = 0;
        this.stop();
        switch (action) {
            case ACTIVE:
                currentFramePositions = activeFramePositions;
                break;
            case IN_ACTIVE:
            case STOPPED:
                currentFramePositions = inActiveFramePositions;
                break;
        }
        this.play();
    }

    public void active() {
        setAction(ACTION.ACTIVE);
    }

    public void inActive() {
        setAction(ACTION.IN_ACTIVE);
    }

    public void pause() {
        setAction(ACTION.STOPPED);
    }

    public ImageView getImageView() {
        return imageView;
    }

    enum ACTION {
        ACTIVE, IN_ACTIVE, STOPPED
    }

    class Playlist {
        int frameWidth;
        int frameHeight;
        int frameDuration;
        double extraX;
        double extraY;
        private HashMap<String, ArrayList<FramePosition>> lists = new HashMap<>();

        public HashMap<String, ArrayList<FramePosition>> getLists() {
            return lists;
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
}
