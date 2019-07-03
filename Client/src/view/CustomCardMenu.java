package view;

import controller.GraphicalUserInterface;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import models.card.AttackType;
import models.card.CardType;
import models.card.EditableCard;
import models.gui.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static models.gui.UIConstants.*;

public class CustomCardMenu extends Show implements PropertyChangeListener {
    private static Media backgroundMusic = new Media( // TODO: Change music
            new File("resources/music/shop_menu.m4a").toURI().toString()
    );
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static final List<CardType> cardTypes = Arrays.asList(CardType.HERO, CardType.MINION, CardType.SPELL, CardType.USABLE_ITEM);
    private static final String ICON_PATH = "resources/icons";
    private static final String TROOPS_PATH = "resources/troopAnimations";
    private static final Map<CardType, ArrayList<String>> sprites = new HashMap<>();

    static {
        ArrayList<String> iconSprites = new ArrayList<>();
        ArrayList<String> troopSprites = new ArrayList<>();
        loadFiles(ICON_PATH, iconSprites);
        loadFiles(TROOPS_PATH, troopSprites);
        sprites.put(CardType.HERO, troopSprites);
        sprites.put(CardType.MINION, troopSprites);
        sprites.put(CardType.USABLE_ITEM, iconSprites);
        sprites.put(CardType.SPELL, iconSprites);
    }

    private GridPane cardMakerGrid;
    private DefaultLabel defaultApLabel = new DefaultLabel("AP", DEFAULT_FONT, Color.WHITE);
    private DefaultLabel defaultHpLabel = new DefaultLabel("HP", DEFAULT_FONT, Color.WHITE);

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
    private DefaultSpinner<CardType> cardTypeSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(cardTypes));
    private DefaultSpinner<AttackType> attackTypeSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(AttackType.values()));
    private DefaultSpinner<ComboState> hasComboSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(ComboState.values()));
    private DefaultSpinner<Integer> defaultHpSpinner = new DefaultSpinner<>(1, 50, 1);
    private DefaultSpinner<Integer> rangeSpinner = new DefaultSpinner<>(2, 4, 2);
    private DefaultSpinner<Integer> defaultApSpinner = new DefaultSpinner<>(0, 15, 0);
    private DefaultSpinner<Integer> mannaPointSpinner = new DefaultSpinner<>(0, 9, 0);
    private DefaultSpinner<String> spriteSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(sprites.get(cardTypeSpinner.getValue())));
    private NumberField priceField = new NumberField("price");
    private EditableCard currentCard = new EditableCard();
    private EditableCardPane cardPane;

    {
        currentCard.setSpriteName(spriteSpinner.getValue());
        currentCard.setType(cardTypeSpinner.getValue());
        currentCard.setAttackType(attackTypeSpinner.getValue());
        currentCard.setDefaultAp(defaultApSpinner.getValue());
        currentCard.setDefaultHp(defaultHpSpinner.getValue());
        currentCard.setHasCombo(hasComboSpinner.getValue().hasCombo);
        currentCard.setMannaPoint(mannaPointSpinner.getValue());
        currentCard.setPrice(priceField.getValue());
        currentCard.setRange(rangeSpinner.getValue());
        try {
            cardPane = new EditableCardPane(currentCard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public CustomCardMenu() {
        preProcess();
        try {
            root.setBackground(UIConstants.DEFAULT_ROOT_BACKGROUND);
            BorderPane background = BackgroundMaker.getMenuBackground();
            BackButton backButton = new BackButton(BACK_EVENT);

            cardMakerGrid = new GridPane();
            cardMakerGrid.setHgap(DEFAULT_SPACING * 2);
            cardMakerGrid.setVgap(DEFAULT_SPACING * 2);
            cardMakerGrid.setMaxSize(1000 * SCALE, 1000 * SCALE);

            cardMakerGrid.add(name, 0, 0, 2, 1);
            cardMakerGrid.add(description, 0, 1, 2, 1);
            cardMakerGrid.add(cardTypeSpinner, 0, 2, 2, 1);
            cardMakerGrid.add(priceField, 0, 3, 2, 1);
            cardMakerGrid.add(new DefaultSeparator(Orientation.HORIZONTAL), 0, 4, 2, 1);
            setType(cardTypeSpinner.getValue());

            cardMakerGrid.add(new DefaultSeparator(Orientation.VERTICAL), 2, 0, 1, 9);

            cardMakerGrid.add(cardPane, 3, 0, 2, 8);
            cardMakerGrid.add(new DefaultLabel("SPRITE", DEFAULT_FONT, Color.WHITE), 3, 8);
            cardMakerGrid.add(spriteSpinner, 4, 8);

            DefaultContainer container = new DefaultContainer(cardMakerGrid);

            AnchorPane sceneContents = new AnchorPane(background, container, backButton);
            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void preProcess() {
        currentCard.addListener(this);
        currentCard.addListener(cardPane);
        name.textProperty().addListener((observable, oldValue, newValue) -> currentCard.setName(newValue));
        description.textProperty().addListener((observable, oldValue, newValue) -> currentCard.setDescription(newValue));
        cardTypeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> currentCard.setType(newValue));
        spriteSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> currentCard.setSpriteName(newValue)));
        attackTypeSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> setAttackType(newValue)));
    }

    @Override
    public void show() {
        super.show();
        BackgroundMaker.makeMenuBackgroundFrozen();
        GraphicalUserInterface.getInstance().setBackgroundMusic(backgroundMusic);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "type":
                setType((CardType) evt.getNewValue());
                break;
        }
    }

    private void setType(CardType newValue) {
        switch (newValue) {
            case HERO:
                cardMakerGrid.add(defaultApLabel, 0, 5);
                cardMakerGrid.add(defaultApSpinner, 1, 5);

                cardMakerGrid.add(defaultHpLabel, 0, 6);
                cardMakerGrid.add(defaultHpSpinner, 1, 6);

                cardMakerGrid.add(attackTypeSpinner, 0, 7, 2, 1);
        }
    }

    private void setAttackType(AttackType newValue) {
        switch (newValue) {
            case MELEE:

                break;
            case RANGED: case HYBRID:

                break;
        }
    }

    enum ComboState {
        COMBO(true),
        NORMAL(false);

        private final boolean hasCombo;

        ComboState(boolean hasCombo) {
            this.hasCombo = hasCombo;
        }
    }
}
