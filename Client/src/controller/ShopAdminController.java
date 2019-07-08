package controller;

import models.account.Collection;
import models.card.Card;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShopAdminController {
    private static ShopAdminController controller;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private ShopAdminController() {
    }

    public static ShopAdminController getInstance() {
        if (controller == null) {
            controller = new ShopAdminController();
            ShopController.getInstance();
        }
        return controller;
    }

    public void changeValueRequest(Card card, int newValue) {
        System.out.println(card.getName() + " " + newValue);
    }

    void setOriginalCards(Collection old, Collection newValue) {
        support.firePropertyChange("original_cards", old, newValue);
    }

    void addCard(Card card) {
        support.firePropertyChange("add_card", null, card);
    }

    public void setValue(String cardName, int newValue) {
        Card card = ShopController.getInstance().getOriginalCards().getCard(cardName);
        if (card == null) return;
        card.setRemainingNumber(newValue);
    }

    public void addListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
