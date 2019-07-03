package models.gui;


public class NumberField extends NormalField {
    public NumberField(String text) {
        super(text);
        textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setText(oldValue);
            }
        }));
    }

    public int getValue() {
        return getText().length() == 0 ? 0 : Integer.parseInt(getText());
    }
}
