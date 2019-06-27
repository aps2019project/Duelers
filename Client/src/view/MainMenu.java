package view;

import controller.Client;
import controller.MainMenuController;
import javafx.scene.layout.*;
import models.gui.*;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenu extends Show {
    private static final String LOGOUT_ICON_URL = "resources/ui/icon_logout.png";
    private static MainMenu menu;
    private static final MenuItem[] items = {
            new MenuItem(0, "PLAY", "Single player, multiplayer", event -> PlayMenu.getInstance().show()),
            new MenuItem(1, "PROFILE", "See you profile information", event -> menu.showProfileDialog()),
            new MenuItem(2, "SHOP", "Buy or sell every card you want", event -> new ShopMenu().show()),
            new MenuItem(3, "COLLECTION", "View your cards or build a deck", event -> new CollectionMenu().show()),
            new MenuItem(4, "CUSTOM CARD", "Design your card with your own taste", event -> {
                    CustomCardMakerMenu.getInstance().show();
            }),
            new MenuItem(5, "SETTING", "Change game properties", event -> {
            }),
    };

    public MainMenu() {
        menu = this;
        try {
            root.setBackground(UIConstants.DEFAULT_ROOT_BACKGROUND);

            BorderPane background = BackgroundMaker.getMenuBackground();
            MainMenuBox menuBox = new MainMenuBox(items);

            AnchorPane sceneContents = new AnchorPane(background, menuBox);

            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showProfileDialog() {
        BackgroundMaker.makeMenuBackgroundFrozen();
        try {
            GridPane profileGrid = new ProfileGrid(Client.getInstance().getAccount());

            DialogBox dialogBox = new DialogBox(profileGrid);
            DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

            dialogContainer.show(root);

            dialogBox.makeButton("LOGOUT", event -> {
                dialogContainer.close();
                MainMenuController.getInstance().logout();
                new LoginMenu().show();

            }, LOGOUT_ICON_URL);
            makeDialogClosable(dialogBox, dialogContainer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        BackgroundMaker.makeMenuBackgroundUnfrozen();
    }

    private void makeDialogClosable(DialogBox dialogBox, DialogContainer dialogContainer) {
        AtomicBoolean shouldBeClosed = new AtomicBoolean(true);
        dialogContainer.makeClosable(shouldBeClosed, event -> BackgroundMaker.makeMenuBackgroundUnfrozen());
        dialogBox.preventClosingOnClick(shouldBeClosed);
    }
}
