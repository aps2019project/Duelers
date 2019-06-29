package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.gui.DialogBox;
import models.gui.DialogContainer;
import models.gui.DialogText;

public class ErrorView {
    private final AnchorPane root;
    private DialogContainer dialogContainer;

    ErrorView(AnchorPane root) {
        this.root = root;
    }

    public void show(String error, String buttonText, EventHandler<? super MouseEvent> event) {
        DialogText errorLabel = new DialogText("Error!");
        DialogText errorMessage = new DialogText(error);

        DialogBox dialogBox = new DialogBox(errorLabel, errorMessage);
        dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton(buttonText, e -> {
            event.handle(e);
            dialogContainer.close();
        });

        dialogContainer.show();
    }
}
