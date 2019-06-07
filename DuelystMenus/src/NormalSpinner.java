import javafx.scene.control.Spinner;

class NormalSpinner extends Spinner<Integer> {

    NormalSpinner(int min, int max, int initialValue) {
        super(min, max, initialValue);
        getEditor().setFont(Constants.DEFAULT_FONT);
    }
}
