package view;

import controller.Client;
import controller.CustomGameMenuController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.card.Deck;
import models.game.GameType;
import models.gui.*;

import java.io.FileNotFoundException;
import java.util.List;

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

    private String[] deckNames;

    private CustomGameMenu() throws FileNotFoundException {
        super(items, BACKGROUND_URL, BACK_EVENT);
        initializeDecks();
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

    private void initializeDecks() {
        List<Deck> decks = Client.getInstance().getAccount().getDecks();
        deckNames = new String[decks.size()];
        for (int i = 0; i < deckNames.length; i++) {
            deckNames[i] = decks.get(i).getName();
        }
    }

    private void showKillHeroDialog() {
        DialogText text = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogBox dialogBox = new DialogBox(text, listView);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            CustomGameMenuController.getInstance().startGame(
                    listView.getSelectionModel().getSelectedItem(), GameType.KILL_HERO, 0
            );
            dialogContainer.close();
        });
        dialogContainer.show();
        dialogBox.makeClosable(dialogContainer);
    }

    private void showSingleFlagDialog() {
        DialogText text = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogBox dialogBox = new DialogBox(text, listView);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            CustomGameMenuController.getInstance().startGame(
                    listView.getSelectionModel().getSelectedItem(), GameType.A_FLAG, 1
            );
            dialogContainer.close();
        });
        dialogContainer.show();
        dialogBox.makeClosable(dialogContainer);
    }

    private void showMultiFlagDialog() {
        DialogText deckText = new DialogText("Please choose one of your decks to be as opponent's deck");
        DeckListView listView = new DeckListView(deckNames);
        DialogText flagNumText = new DialogText("Please set number of flags in the game");
        FlagSpinner flagNumSpinner = new FlagSpinner();

        DialogBox dialogBox = new DialogBox(deckText, listView, flagNumText, flagNumSpinner);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("START", buttonEvent -> {
            if (listView.getSelectionModel().getSelectedItem() == null) return;
            CustomGameMenuController.getInstance().startGame(
                    listView.getSelectionModel().getSelectedItem(), GameType.SOME_FLAG, flagNumSpinner.getValue()
            );
            dialogContainer.close();
        });
        dialogContainer.show();
        dialogBox.makeClosable(dialogContainer);
    }
}
