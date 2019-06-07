package models;

import javafx.scene.text.Text;

public class DialogText extends Text {

    public DialogText(String text) {
        super(text);
        setFont(Constants.DIALOG_TEXT_FONT);
        setEffect(Constants.WHITE_TEXT_SHADOW);
    }
}