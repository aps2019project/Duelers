package view;

import controller.Client;
import controller.GraphicalUserInterface;
import controller.SoundEffectPlayer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public abstract class Show {
    final public AnchorPane root = new AnchorPane();

    public void show() {
        GraphicalUserInterface.getInstance().changeScene(root);
        try {
            SoundEffectPlayer.getInstance().playSound(SoundEffectPlayer.SoundName.enter_page);
        }catch (Exception ignored){
        }
        Client.getInstance().setShow(this);
    }

    public void showError(String message, EventHandler<? super MouseEvent> event) {
        showError(message, "OK", event);
    }

    public void showError(String message, String buttonText) {
        showError(message, buttonText, event -> {});
    }

    public void showError(String message) {
        showError(message, "OK", event -> {});
    }

    public void showError(String message, String buttonText, EventHandler<? super MouseEvent> event) {
        ErrorView errorView = new ErrorView(root);
        errorView.show(message, buttonText, event);
    }
}
