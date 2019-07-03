package view;

import controller.GraphicalUserInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
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

import static models.card.CardType.*;
import static models.gui.UIConstants.*;

public class CustomCardMenu extends Show implements PropertyChangeListener {
    private static Media backgroundMusic = new Media( // TODO: Change music
            new File("resources/music/shop_menu.m4a").toURI().toString()
    );
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static final List<CardType> cardTypes = Arrays.asList(HERO, CardType.MINION, CardType.SPELL, CardType.USABLE_ITEM);
    private static final String ICON_PATH = "resources/icons";
    private static final String TROOPS_PATH = "resources/troopAnimations";
    private static final Map<CardType, ObservableList<String>> sprites = new HashMap<>();

    static {
        ObservableList<String> iconSprites = FXCollections.observableArrayList();
        ObservableList<String> troopSprites = FXCollections.observableArrayList();
        loadFiles(ICON_PATH, iconSprites);
        loadFiles(TROOPS_PATH, troopSprites);
        sprites.put(HERO, troopSprites);
        sprites.put(CardType.MINION, troopSprites);
        sprites.put(CardType.USABLE_ITEM, iconSprites);
        sprites.put(CardType.SPELL, iconSprites);
    }

    private GridPane cardMakerGrid;
    private DefaultLabel defaultApLabel = new DefaultLabel("AP", DEFAULT_FONT, Color.WHITE);
    private DefaultLabel defaultHpLabel = new DefaultLabel("HP", DEFAULT_FONT, Color.WHITE);
    private DefaultLabel attackTypeLabel = new DefaultLabel("ATTACK", DEFAULT_FONT, Color.WHITE);
    private DefaultLabel mannaPointLabel = new DefaultLabel("MANNA", DEFAULT_FONT, Color.WHITE);
    private DefaultLabel rangeLabel = new DefaultLabel("RANGE", DEFAULT_FONT, Color.WHITE);

    private static void loadFiles(String path, ObservableList<String> target) {
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
    private DefaultSpinner<Integer> defaultApSpinner = new DefaultSpinner<>(0, 15, 0);
    private DefaultSpinner<Integer> rangeSpinner = new DefaultSpinner<>(2, 4, 2);
    private DefaultSpinner<Integer> mannaPointSpinner = new DefaultSpinner<>(0, 9, 0);
    private DefaultSpinner<String> spriteSpinner = new DefaultSpinner<>(FXCollections.observableArrayList(sprites.get(cardTypeSpinner.getValue())));
    private NumberField priceField = new NumberField("price");
    private EditableCard card = new EditableCard();
    private EditableCardPane cardPane;

    {
        card.setSpriteName(spriteSpinner.getValue());
        card.setType(cardTypeSpinner.getValue());
        card.setAttackType(attackTypeSpinner.getValue());
        card.setDefaultAp(defaultApSpinner.getValue());
        card.setDefaultHp(defaultHpSpinner.getValue());
        card.setHasCombo(hasComboSpinner.getValue().hasCombo);
        card.setMannaPoint(mannaPointSpinner.getValue());
        card.setPrice(priceField.getValue());
        card.setRange(rangeSpinner.getValue());
        try {
            cardPane = new EditableCardPane(card);
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
            cardMakerGrid.setAlignment(Pos.CENTER);
            cardMakerGrid.setHgap(DEFAULT_SPACING * 2);
            cardMakerGrid.setVgap(DEFAULT_SPACING * 2);
            cardMakerGrid.setMaxSize(2000 * SCALE, 1000 * SCALE);

            cardMakerGrid.add(name, 0, 0, 2, 1);
            cardMakerGrid.add(description, 0, 1, 2, 1);
            cardMakerGrid.add(new DefaultLabel("SPRITE", DEFAULT_FONT, Color.WHITE), 0, 2);
            cardMakerGrid.add(cardTypeSpinner, 1, 2);
            cardMakerGrid.add(priceField, 0, 3, 2, 1);
            cardMakerGrid.add(new DefaultSeparator(Orientation.HORIZONTAL), 0, 4, 2, 1);
            setType(cardTypeSpinner.getValue(), cardTypeSpinner.getValue());

            cardMakerGrid.add(new DefaultSeparator(Orientation.VERTICAL), 2, 0, 1, 10);

            cardMakerGrid.add(cardPane, 3, 0, 2, 9);
            cardMakerGrid.add(new DefaultLabel("SPRITE", DEFAULT_FONT, Color.WHITE), 3, 9);
            cardMakerGrid.add(spriteSpinner, 4, 9);

            DefaultContainer container = new DefaultContainer(cardMakerGrid);

            AnchorPane sceneContents = new AnchorPane(background, container, backButton);
            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void preProcess() {
        card.addListener(this);
        card.addListener(cardPane);
        name.textProperty().addListener((observable, oldValue, newValue) -> card.setName(newValue));
        description.textProperty().addListener((observable, oldValue, newValue) -> card.setDescription(newValue));
        cardTypeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> card.setType(newValue));
        spriteSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setSpriteName(newValue)));
        attackTypeSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> setAttackType(newValue)));
        defaultApSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setDefaultAp(newValue)));
        defaultHpSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setDefaultHp(newValue)));
        mannaPointSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setMannaPoint(newValue)));
        rangeSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setRange(newValue)));
        hasComboSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> card.setHasCombo(newValue.hasCombo)));
        priceField.textProperty().addListener(((observable, oldValue, newValue) -> card.setPrice(newValue.length() == 0 ? 0 : Integer.parseInt(newValue))));
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
                setType((CardType) evt.getOldValue(), (CardType) evt.getNewValue());
                break;
        }
    }

    private void setType(CardType oldValue, CardType newValue) {
        if (!sprites.get(oldValue).equals(sprites.get(newValue))) {
            spriteSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(sprites.get(newValue)));
        }
        switch (newValue) {
            case HERO:
                cardMakerGrid.getChildren().removeAll(
                        defaultApLabel, defaultApSpinner,
                        defaultHpLabel, defaultHpSpinner,
                        attackTypeLabel, attackTypeSpinner,
                        mannaPointLabel, mannaPointSpinner
                );
                cardMakerGrid.add(defaultApLabel, 0, 5);
                cardMakerGrid.add(defaultApSpinner, 1, 5);

                cardMakerGrid.add(defaultHpLabel, 0, 6);
                cardMakerGrid.add(defaultHpSpinner, 1, 6);

                cardMakerGrid.add(attackTypeLabel, 0, 7);
                cardMakerGrid.add(attackTypeSpinner, 1, 7);
                break;
            case MINION:
                cardMakerGrid.getChildren().removeAll(
                        defaultApLabel, defaultApSpinner,
                        defaultHpLabel, defaultHpSpinner,
                        attackTypeLabel, attackTypeSpinner,
                        mannaPointLabel, mannaPointSpinner
                );
                cardMakerGrid.add(defaultApLabel, 0, 5);
                cardMakerGrid.add(defaultApSpinner, 1, 5);

                cardMakerGrid.add(defaultHpLabel, 0, 6);
                cardMakerGrid.add(defaultHpSpinner, 1, 6);

                cardMakerGrid.add(attackTypeLabel, 0, 7);
                cardMakerGrid.add(attackTypeSpinner, 1, 7);

                cardMakerGrid.add(mannaPointLabel, 0, 8);
                cardMakerGrid.add(mannaPointSpinner, 1, 8);
                break;
            case SPELL:
                cardMakerGrid.getChildren().removeAll(
                        defaultApLabel, defaultApSpinner,
                        defaultHpLabel, defaultHpSpinner,
                        attackTypeLabel, attackTypeSpinner,
                        mannaPointLabel, mannaPointSpinner
                );
                cardMakerGrid.add(mannaPointLabel, 0, 5);
                cardMakerGrid.add(mannaPointSpinner, 1, 5);
                break;
            case USABLE_ITEM:
                cardMakerGrid.getChildren().removeAll(
                        defaultApLabel, defaultApSpinner,
                        defaultHpLabel, defaultHpSpinner,
                        attackTypeLabel, attackTypeSpinner,
                        mannaPointLabel, mannaPointSpinner
                );
                break;
        }
    }

    private void setAttackType(AttackType newValue) {
        switch (newValue) {
            case MELEE:
                cardMakerGrid.getChildren().removeAll(rangeLabel, rangeSpinner);
                break;
            case RANGED: case HYBRID:
                    cardMakerGrid.add(rangeLabel, 0, 9);
                    cardMakerGrid.add(rangeSpinner, 1, 9);
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
