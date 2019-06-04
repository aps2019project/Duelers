import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class ImageLoader {

    static ImageView loadImage(String url, double width, double height) throws FileNotFoundException {
        ImageView view = new ImageView(new Image(new FileInputStream(url)));
        view.setFitWidth(width);
        view.setFitHeight(height);
        return view;
    }

    static ImageView loadImage(String url, double width, double height, double x, double y) throws FileNotFoundException {
        ImageView view = loadImage(url, width, height);
        view.relocate(x, y);
        return view;
    }
}
