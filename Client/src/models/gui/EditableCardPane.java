package models.gui;

import models.ICard;

import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;

public class EditableCardPane extends CardPane {
    public EditableCardPane(ICard card) throws FileNotFoundException {
        super(card, true, false, null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
