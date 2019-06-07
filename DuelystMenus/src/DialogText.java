import javafx.scene.text.Text;

class DialogText extends Text {

    DialogText(String text) {
        super(text);
        setFont(Constants.DIALOG_TEXT_FONT);
        setEffect(Constants.WHITE_TEXT_SHADOW);
    }
}