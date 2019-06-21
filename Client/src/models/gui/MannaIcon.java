package models.gui;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static models.gui.UIConstants.SCALE;

class MannaIcon extends StackPane {
    private static final Font FONT = Font.font("SansSerif", FontWeight.EXTRA_BOLD, 40 * SCALE);
    private static final double WIDTH = 135 * SCALE;
    private static final double HEIGHT = 148 * SCALE;
    private static Image mannaIcon;

    static {
        try {
            mannaIcon = new Image(new FileInputStream("resources/ui/icon_mana@2x.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    MannaIcon(int mannaPoint) {
        super(
                ImageLoader.makeImageView(mannaIcon, WIDTH, HEIGHT),
                new DefaultLabel(String.valueOf(mannaPoint), FONT, Color.BLACK)
        );
    }
}
