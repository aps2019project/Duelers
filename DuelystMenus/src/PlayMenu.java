import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileNotFoundException;

class PlayMenu {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final double SEPARATOR_OPACITY = 0.3;
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> MainMenu.getInstance().show();
    private static final PlayButtonItem[] items = {
            new PlayButtonItem("resources/menu/playButtons/single_player.jpg", "SINGLE PLAYER",
                    "Story game and custom game, play with AI", event -> SinglePlayerMenu.getInstance().show()),
            new PlayButtonItem("resources/menu/playButtons/multiplayer.jpg", "MULTIPLAYER",
                    "Play with your friends and earn money", event -> MultiPlayerMenu.getInstance().show())
    };
    private static PlayMenu menu;
    private AnchorPane root = new AnchorPane();
    private Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    PlayMenu(PlayButtonItem[] items, String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);
        BorderPane container = makeContainer(items);
        AnchorPane backButton = ButtonMaker.makeBackButton(backEvent);

        root.getChildren().addAll(background, container, backButton);
    }

    static PlayMenu getInstance() {
        if (menu == null) {
            try {
                menu = new PlayMenu(items, BACKGROUND_URL, BACK_EVENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    private BorderPane makeContainer(PlayButtonItem[] items) {
        BorderPane container = new BorderPane();
        container.setMinSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        container.setMaxSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        BorderPane.setAlignment(container, Pos.CENTER);

        HBox buttonsWrapper = makeButtonsWrapper(items);

        container.setCenter(buttonsWrapper);
        return container;
    }

    private HBox makeButtonsWrapper(PlayButtonItem[] items) {
        HBox buttonsWrapper = new HBox(Constants.DEFAULT_SPACING * 8);
        buttonsWrapper.setAlignment(Pos.CENTER);
        buttonsWrapper.setMaxHeight(Constants.PLAY_MENU_BUTTON_HEIGHT + Constants.PLAY_MENU_PLATE_HEIGHT);

        for (PlayButtonItem item : items) {
            VBox buttonBox = makePlayButtonBox(item);
            buttonsWrapper.getChildren().add(buttonBox);
        }
        return buttonsWrapper;
    }

    private VBox makePlayButtonBox(PlayButtonItem item) {
        ImageView plate = ImageLoader.makeImageView(
                Constants.PLATE_IMAGE, Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_PLATE_HEIGHT
        );

        StackPane imageZone = makeImageZone(item);

        VBox buttonBox = new VBox(Constants.DEFAULT_SPACING * (-3), imageZone, plate);
        buttonBox.setEffect(Constants.PLAY_MENU_BOX_SHADOW);
        return buttonBox;
    }

    private StackPane makeImageZone(PlayButtonItem item) {
        Region space = makeSpace();
        Label title = makeTitle(item);
        Separator separator = makeSeparator();
        Text description = makeDescription(item);

        VBox textBox = new VBox(Constants.DEFAULT_SPACING * 2, space, title, separator, description);
        textBox.setAlignment(Pos.CENTER);

        Vignette vignette = new Vignette(Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_BUTTON_HEIGHT);

        StackPane imageZone = new StackPane(item.imageView, vignette, textBox);


        imageZone.setOnMouseEntered(event -> {
            item.imageView.setEffect(Constants.PLAY_MENU_HOVER_EFFECT);
            imageZone.setCursor(Constants.SELECT_CURSOR);
            title.setEffect(Constants.MENU_ITEM_SHADOW);
        });

        imageZone.setOnMouseExited(event -> {
            item.imageView.setEffect(null);
            imageZone.setCursor(Constants.DEFAULT_CURSOR);
            title.setEffect(null);
        });

        imageZone.setOnMouseClicked(item.event);
        return imageZone;
    }

    private Text makeDescription(PlayButtonItem item) {
        Text description = new Text(item.description);
        description.setFont(Constants.MENU_HINT_FONT);
        description.setFill(Color.WHITE);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(Constants.PLAY_MENU_BUTTON_WIDTH - Constants.DEFAULT_SPACING * 2);
        return description;
    }

    private Region makeSpace() {
        Region space = new Region();
        space.setMinHeight(Constants.PLAY_MENU_BUTTON_HEIGHT * 0.75);
        return space;
    }

    private Separator makeSeparator() {
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setOpacity(SEPARATOR_OPACITY);
        return separator;
    }

    private Label makeTitle(PlayButtonItem item) {
        Label title = new Label(item.title);
        title.setFont(Constants.PLAY_MENU_ITEM_FONT);
        title.setTextFill(Color.WHITE);
        return title;
    }

    void show() {
        Main.setScene(scene);
    }
}
