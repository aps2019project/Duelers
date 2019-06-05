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
    private static final double SEPARATOR_OPACITY = 0.3;
    private static PlayMenu menu;
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

    static PlayMenu getInstance() {
        if (menu == null) {
            try {
                menu = new PlayMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    private PlayMenu() throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.getPlayBackground();
        BorderPane container = makeContainer();
        AnchorPane backButton = ButtonMaker.makeBackButtonPane(event -> MainMenu.getInstance().show());

        root.getChildren().addAll(background, container, backButton);
    }

    void show() {
        Main.setScene(scene);
    }

    private BorderPane makeContainer() {
        BorderPane container = new BorderPane();
        container.setMinSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        container.setMaxSize(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        BorderPane.setAlignment(container, Pos.CENTER);

        HBox buttonsWrapper = makeButtonsWrapper();

        container.setCenter(buttonsWrapper);
        return container;
    }

    private HBox makeButtonsWrapper() {
        HBox buttonsWrapper = new HBox(Constants.DEFAULT_SPACING * 8);
        buttonsWrapper.setAlignment(Pos.CENTER);
        buttonsWrapper.setMaxHeight(Constants.PLAY_MENU_BUTTON_HEIGHT + Constants.PLAY_MENU_PLATE_HEIGHT);

        for (ButtonItem item : ButtonItem.items) {
            VBox buttonBox = makeButtonBox(item);
            buttonsWrapper.getChildren().add(buttonBox);
        }
        return buttonsWrapper;
    }

    private VBox makeButtonBox(ButtonItem item) {
        ImageView plate = ImageLoader.makeImageView(
                Constants.PLATE_IMAGE, Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_PLATE_HEIGHT
        );

        StackPane imageZone = makeImageZone(item);

        VBox buttonBox = new VBox(Constants.DEFAULT_SPACING * (-3), imageZone, plate);
        buttonBox.setEffect(Constants.PLAY_MENU_BOX_SHADOW);
        return buttonBox;
    }

    private StackPane makeImageZone(ButtonItem item) {
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

    private Text makeDescription(ButtonItem item) {
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

    private Label makeTitle(ButtonItem item) {
        Label title = new Label(item.title);
        title.setFont(Constants.PLAY_MENU_ITEM_FONT);
        title.setTextFill(Color.WHITE);
        return title;
    }

    private static class ButtonItem {
        private static final ButtonItem[] items = {
                new ButtonItem("resources/menu/playButtons/single_player.jpg", "SINGLE PLAYER",
                        "Story game and custom game, play with AI", event -> {}),
                new ButtonItem("resources/menu/playButtons/multiplayer.jpg", "MULTIPLAYER",
                        "Play with your friends and earn money", event -> {})
        };

        private final String title;
        private final String description;
        private final EventHandler<? super MouseEvent> event;
        private ImageView imageView;

        private ButtonItem(String url, String title, String description, EventHandler<? super MouseEvent> event) {
            try {
                this.imageView = ImageLoader.loadImage(url, Constants.PLAY_MENU_BUTTON_WIDTH, Constants.PLAY_MENU_BUTTON_HEIGHT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.title = title;
            this.description = description;
            this.event = event;
        }
    }
}
