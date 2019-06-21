package models.gui;

import controller.ShopController;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.card.Card;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static models.gui.UIConstants.SCALE;

public class CardsGrid extends GridPane {
    private static final double BUTTONS_WIDTH = 506 * SCALE;
    private static final int COLUMN_NUMBER = 4;

    public CardsGrid(ArrayList<Card> cards, boolean showPrice) throws FileNotFoundException {
        setHgap(UIConstants.DEFAULT_SPACING * 5);
        setVgap(UIConstants.DEFAULT_SPACING * 5);

        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            VBox shopCardBox = new VBox(-UIConstants.DEFAULT_SPACING);
            shopCardBox.setAlignment(Pos.CENTER);

            CardPane cardPane = new CardPane(card, showPrice);

            HBox buttonsBox = new HBox(UIConstants.DEFAULT_SPACING,
                    new OrangeButton("BUY", event -> ShopController.getInstance().buy(card.getName())),
                    new OrangeButton("SELL", event -> ShopController.getInstance().sell(card.getName()))
            );
            buttonsBox.setMaxWidth(BUTTONS_WIDTH);

            shopCardBox.getChildren().addAll(cardPane, buttonsBox);

            add(
                    shopCardBox, i % COLUMN_NUMBER, i / COLUMN_NUMBER
            );
        }
    }
}
