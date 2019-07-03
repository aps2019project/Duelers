package view;

import controller.GraphicalUserInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import models.card.CardType;
import models.card.EditableCard;
import models.gui.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static models.gui.UIConstants.DEFAULT_SPACING;

public class CustomCardMenu extends Show implements PropertyChangeListener {
    private static Media backgroundMusic = new Media( // TODO: Change music
            new File("resources/music/shop_menu.m4a").toURI().toString()
    );
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static final CardType[] cardTypes = {CardType.HERO, CardType.MINION, CardType.SPELL, CardType.USABLE_ITEM};
    private static final String ICON_PATH = "resources/icons";
    private static final String TROOPS_PATH = "resources/troopAnimations";
    private static ArrayList<String> iconSprites;
    private static ArrayList<String> troopSprites;

    static {
        loadFiles(ICON_PATH, iconSprites);
        loadFiles(TROOPS_PATH, troopSprites);
    }

    private static void loadFiles(String path, ArrayList<String> target) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .filter(file -> file.getName().contains(".png"))
                    .map(file -> file.getName().replace(".png", ""))
                    .forEach(target::add);
        }
    }

    private NormalField name = new NormalField("name");
    private NormalField description = new NormalField("description");
    private Spinner<CardType> cardTypeSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(cardTypes));
    private EditableCard currentCard = new EditableCard();
    private EditableCardPane cardPane;

    {
        try {
            cardPane = new EditableCardPane(currentCard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    CustomCardMenu() {
        currentCard.addListener(this);
        currentCard.addListener(cardPane);
        try {
            root.setBackground(UIConstants.DEFAULT_ROOT_BACKGROUND);
            BorderPane background = BackgroundMaker.getMenuBackground();
            BackButton backButton = new BackButton(BACK_EVENT);

            GridPane cardMakerGrid = new GridPane();
            cardMakerGrid.setHgap(DEFAULT_SPACING * 2);
            cardMakerGrid.setVgap(DEFAULT_SPACING * 2);

            cardMakerGrid.add(name, 0, 0, 2, 1);
            cardMakerGrid.add(description, 0, 1, 2, 1);
            cardMakerGrid.add(cardTypeSpinner, 0, 1, 2, 1);

            prepareCardPane();
            cardMakerGrid.add(cardPane, 2, 0, 1, 8);

            DefaultContainer container = new DefaultContainer(null);

            AnchorPane sceneContents = new AnchorPane(background, backButton);
            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void prepareCardPane() {
        name.textProperty().addListener((observable, oldValue, newValue) -> cardPane.setName(newValue));
        description.textProperty().addListener((observable, oldValue, newValue) -> cardPane.setDescription(newValue));
        cardTypeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            cardPane.setType(newValue);
        });
    }

    @Override
    public void show() {
        super.show();
        BackgroundMaker.makeMenuBackgroundFrozen();
        GraphicalUserInterface.getInstance().setBackgroundMusic(backgroundMusic);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
