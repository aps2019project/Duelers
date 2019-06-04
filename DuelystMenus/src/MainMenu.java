import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class MainMenu {
    private static final String DUELYST_LOGO_URL = "resources/ui/brand_duelyst.png";
    private static final String[][] menuTexts = {
            {"PLAY", "Single player, multiplayer"},
            {"PROFILE", "See you profile information"},
            {"SHOP", "Buy or sell cards"},
            {"COLLECTION", "View your cards or build a deck"},
            {"CUSTOM CARD", "Design your card with your own taste"},
            {"SETTING", "Change game properties"}
    };
    private static final Background MENU_HINT_BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(40, 40, 40), new CornerRadii(Constants.DEFAULT_SPACING * 3), Insets.EMPTY)
    );
    private static AnchorPane root = new AnchorPane();
    private static Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
    private Image menuImage;
    private Image hoverRing;

    void show() throws FileNotFoundException {
        Main.setScene(scene);
        root.setBackground(Constants.ROOT_BACKGROUND);

        BorderPane background = BackgroundMaker.makeMenuBackground();

        ImageView brandView = ImageLoader.loadImage(
                DUELYST_LOGO_URL, Constants.DUELYST_LOGO_WIDTH, Constants.DUELYST_LOGO_HEIGHT
        );

        GridPane menuGrid = new GridPane();
        menuGrid.setVgap(Constants.DEFAULT_SPACING * 3);
        menuGrid.setHgap(Constants.DEFAULT_SPACING * 3);

        menuImage = new Image(new FileInputStream("resources/ui/menu_item.png"));
        hoverRing = new Image(new FileInputStream("resources/ui/glow_ring.png"));

        for (int i = 0; i < menuTexts.length; i++) {
            Node[] row = makeRow(menuTexts[i]);
            menuGrid.addRow(i, row);
        }

        VBox menuBox = new VBox(Constants.DEFAULT_SPACING * 5, brandView, menuGrid);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.relocate(Constants.MAIN_MENU_BOX_X, Constants.MAIN_MENU_BOX_Y);
        menuBox.setPadding(new Insets(Constants.DEFAULT_SPACING * 6));

        root.getChildren().addAll(background, menuBox);
    }

    private Node[] makeRow(String[] menuItem) {
        VBox textWrapper = makeHintBox(menuItem[1]);
        ImageView menuView = ImageLoader.makeImageView(
                menuImage, Constants.MENU_ITEM_IMAGE_SIZE, Constants.MENU_ITEM_IMAGE_SIZE
        );
        ImageView ringView = ImageLoader.makeImageView(
                hoverRing, Constants.MENU_ITEM_IMAGE_SIZE, Constants.MENU_ITEM_IMAGE_SIZE
        );
        ringView.setVisible(false);
        RotateTransition rotate = makeRotationAnimation(ringView);
        Label label = makeMenuLabel(menuItem[0]);

        label.setOnMouseEntered(event -> {
            menuView.setOpacity(0.6);
            ringView.setVisible(true);
            label.setCursor(Cursor.HAND);
            label.setEffect(Constants.MENU_ITEM_SHADOW);
            rotate.play();
            textWrapper.setVisible(true);
        });

        label.setOnMouseExited(event -> {
            menuView.setOpacity(1);
            ringView.setVisible(false);
            label.setCursor(Cursor.DEFAULT);
            label.setEffect(null);
            rotate.pause();
            textWrapper.setVisible(false);
        });

        return new Node[]{textWrapper, new StackPane(menuView, ringView), label};
    }

    private Label makeMenuLabel(String itemText) {
        Label label = new Label(itemText);
        label.setFont(Constants.MENU_ITEM_FONT);
        label.setTextFill(Color.WHITE);
        return label;
    }

    private RotateTransition makeRotationAnimation(ImageView ringView) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), ringView);
        rotate.setByAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(Animation.INDEFINITE);
        return rotate;
    }

    private VBox makeHintBox(String hintText) {
        Text hint = makeHintText(hintText);

        VBox wrapper = new VBox(hint);
        wrapper.setPadding(new Insets(Constants.DEFAULT_SPACING));
        wrapper.setBackground(MENU_HINT_BACKGROUND);
        wrapper.setVisible(false);
        return wrapper;
    }

    private Text makeHintText(String text) {
        Text hint = new Text(text);
        hint.setWrappingWidth(Constants.MENU_HINT_WIDTH);
        hint.setTextAlignment(TextAlignment.CENTER);
        hint.setFont(Constants.MENU_HINT_FONT);
        hint.setFill(Color.WHITE);
        return hint;
    }
}
