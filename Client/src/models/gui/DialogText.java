package models.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DialogText extends Text {

    public DialogText(String text) {
        super(text);
        setFont(UIConstants.DIALOG_TEXT_FONT);
        setEffect(UIConstants.WHITE_TEXT_SHADOW);
    }

    public DialogText(String text, Color fill) {
        this(text);
        this.setFill(fill);
    }
}