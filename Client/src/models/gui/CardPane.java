package models.gui;

import controller.Client;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.ICard;
import models.account.Collection;
import models.card.CardType;
import models.card.Deck;
import view.BattleView.CardAnimation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

import static models.gui.CardBackground.*;
import static models.gui.CardDetailBox.DESCRIPTION_COLOR;
import static models.gui.CardDetailBox.DESCRIPTION_FONT;
import static models.gui.UIConstants.SCALE;

public class CardPane extends AnchorPane implements PropertyChangeListener {
    private static final double AP_X = 120 * SCALE;
    private static final double HP_X = 360 * SCALE;
    private static final double AP_HP_Y = 360 * SCALE;
    private static final Font AP_HP_FONT = Font.font("SansSerif", FontWeight.SEMI_BOLD, 30 * SCALE);
    private static final double SPRITE_CENTER_X = GLOW_WIDTH / 2;
    private static final double SPRITE_CENTER_Y = 180 * SCALE;
    private final CardBackground background;
    private final CardDetailBox detailBox;
    private final CardAnimation cardAnimation;

    Deck deck;
    ICard card;
    DefaultLabel countLabel;
    int oldCount;

    public CardPane(ICard card, boolean showPrice, boolean showCount, Deck deck) throws FileNotFoundException {
        this.card = card;
        this.deck = deck;
        setPrefSize(GLOW_WIDTH, GLOW_HEIGHT);

        background = new CardBackground(card);

        detailBox = new CardDetailBox(card);
        getChildren().addAll(background, detailBox);

        if (card.getType() == CardType.HERO || card.getType() == CardType.MINION) {
            Label apLabel = new DefaultLabel(
                    String.valueOf(card.getDefaultAp()), AP_HP_FONT, Color.WHITE, AP_X, AP_HP_Y
            );
            Label hpLabel = new DefaultLabel(
                    String.valueOf(card.getDefaultHp()), AP_HP_FONT, Color.WHITE, HP_X, AP_HP_Y
            );
            getChildren().addAll(apLabel, hpLabel);
        }

        if (card.getType() == CardType.SPELL || card.getType() == CardType.MINION) {
            StackPane mannaPane = new MannaIcon(card.getMannaPoint());
            getChildren().add(mannaPane);
        }

        if (showPrice) {
            VBox priceBox = new PriceBox(card.getPrice());
            getChildren().add(priceBox);
        }

        if (showCount) {
            count(card);
            showCount();
        }

        cardAnimation = new CardAnimation(this, card, SPRITE_CENTER_Y, SPRITE_CENTER_X);

        setOnMouseEntered(event -> {
            cardAnimation.inActive();
            background.showGlow();
            setCursor(UIConstants.SELECT_CURSOR);
        });
        setOnMouseExited(event -> {
            cardAnimation.stop();
            background.hideGlow();
            setCursor(UIConstants.DEFAULT_CURSOR);
        });
    }

    void count(ICard card) {
        Client.getInstance().getAccount().addPropertyChangeListener(this);
        oldCount = Client.getInstance().getAccount().getCollection().count(card.getName());
    }

    private void showCount() {
        countLabel = new DefaultLabel(
                "X " + oldCount,
                DESCRIPTION_FONT, DESCRIPTION_COLOR
        );

        countLabel.relocate(CARD_WIDTH * 0.53, CARD_HEIGHT * 0.98);
        getChildren().add(countLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("collection")) {
            int newCount = ((Collection) evt.getNewValue()).count(card.getName());
            if (newCount != oldCount) {
                oldCount = newCount;
                Platform.runLater(() ->
                        countLabel.setText("X " + newCount)
                );
            }
        }
    }

    public void setName(String newValue) {
        detailBox.setName(newValue);
    }

    public void setType(CardType newValue) {
        background.changeType(newValue);
        detailBox.setType(newValue);
    }

    void setDescription(String newValue) {
        detailBox.setDescription(newValue);
    }

    void setSprite(String spriteName) {
        cardAnimation.setSprite(spriteName);
    }
}