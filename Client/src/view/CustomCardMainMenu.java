package view;

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
import models.card.spell.Spell;
import models.gui.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CustomCardMainMenu extends Show {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static CustomCardMainMenu menu;

    private static NormalField name;
    private static NormalField description;
    private static Spinner<CardType> cardTypeSpinner;
    private static ArrayList<Spell> spells;
    private static Spinner<AttackType>  attackTypeSpinner;
    private static Spinner<Integer> defualtHP;
    private static Spinner<Integer> range;
    private static Spinner<Integer> defualtAP;
    private static Spinner<Integer> manaPoint;
    private static NormalField price;
    private static CheckBox hasCombo;
    private static NormalField spriteName;

    private static final EventHandler<? super MouseEvent> CREATE = event -> CustomCardController.getInstance().createCard(
            new Card(name.getText(),description.getText(),spriteName.getText(),cardTypeSpinner.getValue(),spells,
                    defualtAP.getValue(),defualtHP.getValue(),manaPoint.getValue(),Integer.parseInt(price.getText()),
                    attackTypeSpinner.getValue(),range.getValue(),hasCombo.isSelected())
    );



    private CustomCardMainMenu( String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);
        BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);
        BackButton backButton = new BackButton(backEvent);

        AnchorPane sceneContents = new AnchorPane(background, backButton);

        root.getChildren().addAll(sceneContents);
    }

    public static CustomCardMainMenu getInstance() {
        if (menu == null) {
            try {
                menu = new CustomCardMainMenu( BACKGROUND_URL, BACK_EVENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
