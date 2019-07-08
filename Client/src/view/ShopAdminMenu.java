package view;

import controller.ShopAdminController;
import controller.ShopController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import models.gui.ShopAdminList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

class ShopAdminMenu extends Show implements PropertyChangeListener {
    private static Media backgroundMusic = new Media(
            new File("resources/music/shop_menu.m4a").toURI().toString()
    );
    private static ShopAdminMenu menu;
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();

    public static ShopAdminMenu getInstance() {
        if (menu == null) {
            menu = new ShopAdminMenu();
        }
        return menu;
    }

    private ShopAdminMenu() {
        ShopAdminController.getInstance().addListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
