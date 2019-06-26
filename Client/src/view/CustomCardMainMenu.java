package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.gui.*;

import java.io.FileNotFoundException;

public class CustomCardMainMenu extends Show {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/kill_hero.jpg", "MINION",
                    "create minion", event -> SinglePlayerMenu.getInstance().show()),
            new PlayButtonItem("resources/menu/playButtons/single_player.jpg", "HERO",
                    "create hero", event -> MultiPlayerMenu.getInstance().show()),
            new PlayButtonItem("resources/menu/playButtons/single_flag.jpg", "SPELL",
                    "create spell", event -> MultiPlayerMenu.getInstance().show())

    };
    private static CustomCardMainMenu menu;

    CustomCardMainMenu(PlayButtonItem[] items, String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);
        BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);
        DefaultContainer container = new DefaultContainer(new HorizontalButtonsBox(items));
        BackButton backButton = new BackButton(backEvent);

        AnchorPane sceneContents = new AnchorPane(background, container, backButton);

        root.getChildren().addAll(sceneContents);
    }

    public static CustomCardMainMenu getInstance() {
        if (menu == null) {
            try {
                menu = new CustomCardMainMenu(items, BACKGROUND_URL, BACK_EVENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
