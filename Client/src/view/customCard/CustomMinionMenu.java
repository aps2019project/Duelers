package view.customCard;

import controller.CustomCardController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.card.AttackType;
import models.card.Card;
import models.card.CardType;
import models.gui.*;
import view.MainMenu;
import view.Show;

import java.io.FileNotFoundException;

public class CustomMinionMenu extends Show {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> CustomCardMainMenu.getInstance().show();

    private static CustomCardMainMenu menu;

    private static NormalField name;
    private static NormalField description;
    private static Spinner<CardType> cardTypeSpinner;
    private static Spinner<AttackType>  attackTypeSpinner;
    private static Spinner<Integer> defualtHP;
    private static Spinner<Integer> range;
    private static Spinner<Integer> defualtAP;
    private static Spinner<Integer> manaPoint;
    private static NormalField price;
    private static CheckBox hasCombo;
    private static NormalField spriteName;

    private static final EventHandler<? super MouseEvent> CREATE = event -> CustomCardController.getInstance().createCard(new Card());

    private CustomMinionMenu(String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);
        GridPane background = new GridPane(backgroundUrl);
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
