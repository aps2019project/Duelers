package view;

import models.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

class CustomGameMenu extends PlayMenu {
    private static final String BACKGROUND_URL = "resources/menu/background/custom_game_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> SinglePlayerMenu.getInstance().show();
    private static CustomGameMenu menu;
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/kill_hero.jpg", "KILL HERO",
                    "You must kill opponent's hero to win", event -> menu.showKillHeroDialog()),
            new PlayButtonItem("resources/menu/playButtons/single_flag.jpg", "SINGLE FLAG",
                    "You must keep the flag for 6 turns to win", event -> menu.showSingleFlagDialog()),
            new PlayButtonItem("resources/menu/playButtons/multi_flag.jpg", "MULTI FLAG",
                    "You must collect at least half the flags to win", event -> menu.showMultiFlagDialog())
    };

    private String[] deckNames = {
            "strong deck", "spell deck", "custom deck", "alo", "slm", "sdfa", "gfh", "yth", "pkm", "lskdf", "skdfm", "ljf", "dff"
    };

    private CustomGameMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
    }

    public static CustomGameMenu getInstance() {
        if (menu == null) {
            try {
                menu = new CustomGameMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    private void showKillHeroDialog() {
        DialogText text = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogBox dialogBox = new DialogBox(text, listView);
        DialogContainer dialogContainer = new DialogContainer(dialogBox);

        dialogBox.setButtonMouseEvent(buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            System.out.println(listView.getSelectionModel().getSelectedItem());
            //TODO send data to server
            dialogContainer.closeFrom(root);
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void showSingleFlagDialog() {
        DialogText text = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogBox dialogBox = new DialogBox(text, listView);
        DialogContainer dialogContainer = new DialogContainer(dialogBox);

        dialogBox.setButtonMouseEvent(buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            System.out.println("hello");
            //TODO send data to server
            dialogContainer.closeFrom(root);
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void showMultiFlagDialog() {
        DialogText deckText = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogText flagNumText = new DialogText("Please set number of flags in the game");
        NormalSpinner flagNumSpinner = new NormalSpinner(Constants.FLAG_NUM_MIN, Constants.FLAG_NUM_MAX, Constants.FLAG_NUM_DEFAULT);

        DialogBox dialogBox = new DialogBox(deckText, listView, flagNumText, flagNumSpinner);
        DialogContainer dialogContainer = new DialogContainer(dialogBox);

        dialogBox.setButtonMouseEvent(buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            //TODO send data to server
            dialogContainer.closeFrom(root);
        });
        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void makeDialogClosable(DialogBox dialogBox, DialogContainer dialogContainer) {
        AtomicBoolean shouldBeClosed = new AtomicBoolean(true);
        dialogContainer.makeClosable(shouldBeClosed, root);
        dialogBox.preventClosingOnClick(shouldBeClosed);
    }
}
