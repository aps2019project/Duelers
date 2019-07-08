package controller;

import models.Constants;
import models.account.Collection;
import models.card.Card;
import models.message.DataName;
import models.message.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShopAdminController {
    private static ShopAdminController controller;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Collection originalCards;

    private ShopAdminController() {
    }

    public static ShopAdminController getInstance() {
        if (controller == null) {
            controller = new ShopAdminController();
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeGetDataMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, DataName.ORIGINAL_CARDS, 0)
            );
        }
        return controller;
    }

    public void changeValueRequest(String cardName, int newValue) {

    }

    public void setOriginalCards(Collection originalCards) {
        Collection old = this.originalCards;
        this.originalCards = originalCards;
        support.firePropertyChange("original_cards", old, originalCards);
    }

    public void addCard(Card card) {
        originalCards.addCard(card);
        support.firePropertyChange("add_card", null, card);
    }

    public void setValue(String cardName, int newValue) {
        Card card = originalCards.getCard(cardName);
        if (card == null) return;
        card.setRemainingNumber(newValue);
        support.firePropertyChange("set_value", null, card);
    }

    public void addListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
