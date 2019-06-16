package models.gui;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class DefaultText extends Text {

    DefaultText(String text, double wrapWidth, Font font, Paint color) {
        super(text);
        setTextAlignment(TextAlignment.CENTER);
        setWrappingWidth(wrapWidth);
        setFont(font);
        setFill(color);
    }
}
