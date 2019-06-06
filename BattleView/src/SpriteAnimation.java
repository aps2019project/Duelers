import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int rows;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
    private final int frameDuration;
    private int firstIndex, lastIndex;

    private int lastShownIndex;

    SpriteAnimation(
            ImageView imageView,
            int frameDuration, int rows,
            int offsetX, int offsetY,
            int width, int height) {
        this.imageView = imageView;
        this.frameDuration = frameDuration;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setInterpolator(Interpolator.LINEAR);
    }

    public void play(int firstIndex, int lastIndex) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        setCycleDuration(Duration.millis(frameDuration * (firstIndex - lastIndex + 1)));
        this.play();
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.max(firstIndex - (int) (k * (firstIndex - lastIndex + 1)), lastIndex);
        if (index != lastShownIndex) {
            int x = (index / rows) * width + offsetX;
            int y = (index % rows) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastShownIndex = index;
        }
    }
}
