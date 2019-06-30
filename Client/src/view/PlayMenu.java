package view;

import controller.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import models.gui.*;

import java.io.File;
import java.io.FileNotFoundException;

public class PlayMenu extends Show {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static Media backgroundMusic = new Media(
            new File("resources/music/play_menu.m4a").toURI().toString()
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/single_player.jpg", "SINGLE PLAYER",
                    "Story game and custom game, play with AI", event -> SinglePlayerMenu.getInstance().show()),
            new PlayButtonItem("resources/menu/playButtons/multiplayer.jpg", "MULTIPLAYER",
                    "Play with your friends and earn money", event -> MultiPlayerMenu.getInstance().show())
    };
    private static PlayMenu menu;

    PlayMenu(PlayButtonItem[] items, String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);
        DefaultContainer container = new DefaultContainer(new HorizontalButtonsBox(items));
        BackButton backButton = new BackButton(backEvent);

        AnchorPane sceneContents = new AnchorPane(background, container, backButton);

        root.getChildren().addAll(sceneContents);
    }

    @Override
    public void show() {
        super.show();
        GraphicalUserInterface.getInstance().setBackgroundMusic(backgroundMusic);
    }

    public static PlayMenu getInstance() {
        if (menu == null) {
            try {
                menu = new PlayMenu(items, BACKGROUND_URL, BACK_EVENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
