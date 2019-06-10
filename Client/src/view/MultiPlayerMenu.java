package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.gui.*;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

class MultiPlayerMenu extends PlayMenu {
    private static final String BACKGROUND_URL = "resources/menu/background/multiplayer_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> PlayMenu.getInstance().show();
    private static MultiPlayerMenu menu;
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/kill_hero.jpg", "KILL HERO",
                    "You must kill opponent's hero to win", event -> menu.showKillHeroDialog()),
            new PlayButtonItem("resources/menu/playButtons/single_flag.jpg", "SINGLE FLAG",
                    "You must keep the flag for 6 turns to win", event -> menu.showSingleFlagDialog()),
            new PlayButtonItem("resources/menu/playButtons/multi_flag.jpg", "MULTI FLAG",
                    "You must collect at least half the flags to win", event -> menu.showMultiFlagDialog())
    };

    private MultiPlayerMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
    }

    public static MultiPlayerMenu getInstance() {
        if (menu == null) {
            try {
                menu = new MultiPlayerMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    private void showKillHeroDialog() {
        DialogText usernameText = new DialogText("Please enter opponent's username");
        NormalField usernameField = new NormalField("opponent username");
        DialogBox dialogBox = new DialogBox(usernameText, usernameField);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (usernameField.getText().equals("")) return;
            System.out.println(usernameField.getText());
            //TODO send data to server
            dialogContainer.close();
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void showSingleFlagDialog() {
        DialogText usernameText = new DialogText("Please enter opponent's username");
        NormalField usernameField = new NormalField("opponent username");
        DialogBox dialogBox = new DialogBox(usernameText, usernameField);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (usernameField.getText().equals("")) return;
            System.out.println("hello");
            //TODO send data to server
            dialogContainer.close();
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void showMultiFlagDialog() {
        DialogText usernameText = new DialogText("Please enter opponent's username");
        NormalField usernameField = new NormalField("opponent username");
        DialogText flagNumText = new DialogText("Please set number of flags in the game");
        NormalSpinner flagNumSpinner = new NormalSpinner(UIConstants.FLAG_NUM_MIN, UIConstants.FLAG_NUM_MAX, UIConstants.FLAG_NUM_DEFAULT);

        DialogBox dialogBox = new DialogBox(usernameText, usernameField, flagNumText, flagNumSpinner);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (usernameField.getText().equals("")) return;
            //TODO send data to server
            dialogContainer.close();
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void makeDialogClosable(DialogBox dialogBox, DialogContainer dialogContainer) {
        AtomicBoolean shouldBeClosed = new AtomicBoolean(true);
        dialogContainer.makeClosable(shouldBeClosed);
        dialogBox.preventClosingOnClick(shouldBeClosed);
    }
}
