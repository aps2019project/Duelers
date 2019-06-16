package models.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class ProfilePicture extends Rectangle {
    ProfilePicture(String url) throws FileNotFoundException {
        super(UIConstants.PROFILE_PIC_SIZE, UIConstants.PROFILE_PIC_SIZE);
        setArcWidth(UIConstants.PROFILE_PIC_CORNER_RADIUS);
        setArcHeight(UIConstants.PROFILE_PIC_CORNER_RADIUS);

        ImagePattern pattern = new ImagePattern(
                new Image(new FileInputStream(url))
        );

        setFill(pattern);
        setEffect(UIConstants.PROFILE_PIC_SHADOW);
    }
}
