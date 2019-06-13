package models.gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfilePicture extends Rectangle {
    public ProfilePicture(String url) throws FileNotFoundException {
        super(UIConstants.PROFILE_PIC_SIZE, UIConstants.PROFILE_PIC_SIZE);
        setArcWidth(UIConstants.PROFILE_PIC_CORNER_RADIUS);
        setArcHeight(UIConstants.PROFILE_PIC_CORNER_RADIUS);

        ImagePattern pattern = new ImagePattern(
                new Image(new FileInputStream(url))
        );

        setFill(pattern);
        setEffect(new DropShadow(UIConstants.PROFILE_PIC_CORNER_RADIUS, Color.BLACK));
    }
}
