package models.gui;

import javafx.scene.layout.GridPane;
import models.card.Card;
import models.card.Deck;

import java.io.FileNotFoundException;
import java.util.List;

import static models.gui.UIConstants.DEFAULT_SPACING;
import static models.gui.UIConstants.SCALE;

public class DeckCardsGrid extends GridPane {
    private static final int COLUMN_NUMBER = 4;
    private static final double WIDTH = 2350 * SCALE;

    public DeckCardsGrid(List<Card> cards, Deck deck) throws FileNotFoundException {
        setHgap(DEFAULT_SPACING * 5);
        setVgap(DEFAULT_SPACING * 5);
        setMinWidth(WIDTH);
        setMaxWidth(WIDTH);
        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            CardPane cardPane = new DeckCardPane(card, deck);
            add(
                    cardPane, i % COLUMN_NUMBER, i / COLUMN_NUMBER
            );
        }
    }
}
