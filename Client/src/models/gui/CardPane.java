package models.gui;

import controller.Client;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.ICard;
import models.card.CardType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static models.gui.UIConstants.SCALE;

class CardPane extends AnchorPane implements PropertyChangeListener {
    private static final double CARD_WIDTH = 452 * SCALE;
    private static final double CARD_HEIGHT = 592 * SCALE;
    private static final double GLOW_WIDTH = 506 * SCALE;
    private static final double GLOW_HEIGHT = 639 * SCALE;
    private static final double SPRITE_SIZE = 170 * SCALE;
    private static final double DESCRIPTION_WIDTH = 400 * SCALE;
    private static final double AP_X = 120 * SCALE;
    private static final double HP_X = 360 * SCALE;
    private static final double AP_HP_Y = 360 * SCALE;
    private static final double PRICE_Y = 430 * SCALE;
    private static final double SPACE_HEIGHT = 115 * SCALE;
    private static final Font NAME_FONT = Font.font("DejaVu Sans Light", FontWeight.SEMI_BOLD, 28 * SCALE);
    private static final Font TYPE_FONT = Font.font("DejaVu Sans Light", FontWeight.EXTRA_LIGHT, 25 * SCALE);
    private static final Font AP_HP_FONT = Font.font("SansSerif", FontWeight.SEMI_BOLD, 30 * SCALE);
    private static final Font DESCRIPTION_FONT = Font.font("SansSerif", FontWeight.BOLD, 19 * SCALE);
    private static final Color NAME_COLOR = Color.gray(1);
    private static final Color TYPE_COLOR = Color.rgb(133, 199, 202);
    private static final Color DESCRIPTION_COLOR = Color.rgb(133, 199, 202, 0.7);
    private static final Insets BOX_PADDING = new Insets(85 * SCALE, 0, 0, 0);
    private static final HashMap<CardType, Image> background = new HashMap<>();
    private static Image glow;

    static {
        try {
            Image troopBackground = new Image(new FileInputStream("resources/card_backgrounds/troop.png"));
            Image spellBackground = new Image(new FileInputStream("resources/card_backgrounds/spell.png"));
            Image itemBackground = new Image(new FileInputStream("resources/card_backgrounds/item.png"));
            glow = new Image(new FileInputStream("resources/card_backgrounds/glow.png"));

            background.put(CardType.HERO, troopBackground);
            background.put(CardType.MINION, troopBackground);
            background.put(CardType.SPELL, spellBackground);
            background.put(CardType.COLLECTIBLE_ITEM, itemBackground);
            background.put(CardType.USABLE_ITEM, itemBackground);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ICard card;
    private DefaultLabel countLabel;
    private int oldCount;

    //TODO: This code will be cleaned
    CardPane(ICard card, boolean showPrice, boolean showCount) throws FileNotFoundException {
        this.card = card;
        setPrefSize(GLOW_WIDTH, GLOW_HEIGHT);

        ImageView backgroundView = ImageLoader.makeImageView(background.get(card.getType()), CARD_WIDTH, CARD_HEIGHT);
        ImageView glowView = ImageLoader.makeImageView(glow, GLOW_WIDTH, GLOW_HEIGHT);
        glowView.setVisible(false);

        setOnMouseEntered(event -> {
            glowView.setVisible(true);
            setCursor(UIConstants.SELECT_CURSOR);
        });
        setOnMouseExited(event -> {
            glowView.setVisible(false);
            setCursor(UIConstants.DEFAULT_CURSOR);
        });

        VBox spriteBox = new VBox(UIConstants.DEFAULT_SPACING);
        spriteBox.setPadding(BOX_PADDING);
        spriteBox.setMinWidth(GLOW_WIDTH);
        spriteBox.setAlignment(Pos.CENTER);

        // TODO: ADD SPRITE ANIMATION INSTEAD:)  :
        ImageView tempImage = ImageLoader.loadImage("resources/ui/menu_item.png", SPRITE_SIZE, SPRITE_SIZE);

        Label name = new DefaultLabel(card.getName(), NAME_FONT, NAME_COLOR);
        name.setAlignment(Pos.CENTER_LEFT);
        Label type = new DefaultLabel(
                card.getType().toString().replaceAll("_", " "), TYPE_FONT, TYPE_COLOR
        );
        type.setAlignment(Pos.CENTER);

        Text description = new DefaultText(
                card.getDescription(), DESCRIPTION_WIDTH, DESCRIPTION_FONT, DESCRIPTION_COLOR
        );

        spriteBox.getChildren().addAll(tempImage, name, type, new Space(SPACE_HEIGHT), description);

        getChildren().addAll(new StackPane(backgroundView, glowView), spriteBox);

        if (card.getType() == CardType.HERO || card.getType() == CardType.MINION) {
            Label apLabel = new DefaultLabel(String.valueOf(card.getDefaultAp()), AP_HP_FONT, Color.WHITE);
            apLabel.relocate(AP_X, AP_HP_Y);

            Label hpLabel = new DefaultLabel(String.valueOf(card.getDefaultHp()), AP_HP_FONT, Color.WHITE);
            hpLabel.relocate(HP_X, AP_HP_Y);

            getChildren().addAll(apLabel, hpLabel);
        }

        if (showPrice) {
            Label price = new DefaultLabel(
                    "PRICE: " + card.getPrice(), DESCRIPTION_FONT, DESCRIPTION_COLOR
            );

            VBox priceBox = new VBox(price);
            priceBox.setMinWidth(GLOW_WIDTH);
            priceBox.setAlignment(Pos.CENTER);
            priceBox.setLayoutY(PRICE_Y);

            getChildren().add(priceBox);
        }

        if (showCount) {
            Client.getInstance().getAccount().addPropertyChangeListener(this);
            oldCount = Client.getInstance().getAccount().getCollection().count(card.getName());
            countLabel = new DefaultLabel(
                    "X " + oldCount,
                    DESCRIPTION_FONT, DESCRIPTION_COLOR
            );

            countLabel.relocate(CARD_WIDTH * 0.53, CARD_HEIGHT * 0.98);

            getChildren().add(countLabel);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("collection")) {
            int newCount = Client.getInstance().getAccount().getCollection().count(card.getName());
            if (newCount != oldCount) {
                oldCount = newCount;
                Platform.runLater(() ->
                        countLabel.setText("X " + newCount)
                );
            }
        }
    }
}