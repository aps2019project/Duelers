package models.gui;

import javafx.scene.layout.GridPane;
import models.card.Card;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CardsGrid extends GridPane {
    private static final int COLUMN_NUMBER = 4;

    public CardsGrid(ArrayList<Card> cards, boolean showPrice) throws FileNotFoundException {
        setHgap(UIConstants.DEFAULT_SPACING * 5);
        setVgap(UIConstants.DEFAULT_SPACING * 5);

        for (int i = 0; i < cards.size(); i++) {
            add(
                    new CardPane(cards.get(i), showPrice), i % COLUMN_NUMBER, i / COLUMN_NUMBER
            );
        }
    }
}
