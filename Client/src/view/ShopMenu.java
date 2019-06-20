package view;

import controller.ShopController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.account.Collection;
import models.gui.BackgroundMaker;

public class ShopMenu extends Show {
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static ShopMenu menu;
    private Collection originalCards;

    ShopMenu() {
        menu = this;
        setOriginalCards();

        /*try {
            root.setBackground(UIConstants.DEFAULT_ROOT_BACKGROUND);

            BorderPane background = BackgroundMaker.getMenuBackground();
            BackButton backButton = new BackButton(BACK_EVENT);

            CardPane hero = new CardPane(originalCards.getHeroes().get(0), true);
            hero.relocate(500 * SCALE, 500 * SCALE);

            CardPane minion = new CardPane(originalCards.getMinions().get(0), true);
            minion.relocate(1100 * SCALE, 500 * SCALE);

            CardPane spell = new CardPane(originalCards.getSpells().get(0), true);
            spell.relocate(1700 * SCALE, 500 * SCALE);

            CardPane item = new CardPane(originalCards.getItems().get(0), true);
            item.relocate(2300 * SCALE, 500 * SCALE);

            AnchorPane sceneContents = new AnchorPane(background, backButton, hero, minion, spell, item);

            root.getChildren().addAll(sceneContents);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void setOriginalCards() {
        synchronized (ShopController.getInstance()) {
            if (ShopController.getInstance().getOriginalCards() == null) {
                try {
                    ShopController.getInstance().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        originalCards = ShopController.getInstance().getOriginalCards();
    }

    @Override
    public void show() {
        super.show();
        BackgroundMaker.makeMenuBackgroundFrozen();
    }
}
