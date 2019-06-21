package models.gui;

import javafx.scene.layout.GridPane;
import models.card.Card;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static models.gui.UIConstants.SCALE;

public class CollectionCardsGrid extends GridPane {
    private static final int COLUMN_NUMBER = 4;
    private static final double WIDTH = 2350 * SCALE;

    public CollectionCardsGrid(ArrayList<Card> cards) throws FileNotFoundException {
        setHgap(UIConstants.DEFAULT_SPACING * 5);
        setVgap(UIConstants.DEFAULT_SPACING * 5);
        setMinWidth(WIDTH);
        setMaxWidth(WIDTH);

        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            CardPane cardPane = new CardPane(card, false, true);
            add(
                    cardPane, i % COLUMN_NUMBER, i / COLUMN_NUMBER
            );
        }
    }
}
