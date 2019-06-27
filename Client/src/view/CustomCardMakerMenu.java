package view;

import controller.CustomCardController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.card.AttackType;
import models.card.Card;
import models.card.CardType;
import models.card.spell.*;
import models.game.map.Position;
import models.gui.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CustomCardMakerMenu extends Show {
    private static final Background ROOT_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(40, 43, 53), CornerRadii.EMPTY, Insets.EMPTY
            )
    );
    private static final String BACKGROUND_URL = "resources/menu/background/play_background.jpg";
    private static final EventHandler<? super MouseEvent> BACK_EVENT = event -> new MainMenu().show();
    private static CustomCardMakerMenu menu;

    private  NormalField name;
    private  NormalField description;
    private  Spinner<CardType> cardTypeSpinner;
    private  ArrayList<Spell> spells = new ArrayList<>();
    private  Spinner<AttackType>  attackTypeSpinner;
    private  Spinner<Integer> defualtHP;
    private  Spinner<Integer> range;
    private  Spinner<Integer> defualtAP;
    private  Spinner<Integer> manaPoint;
    private  NormalField price;
    private  CheckBox hasCombo;
    private  NormalField spriteName;

    private OrangeButton button = new OrangeButton("add spell",event -> {
        addSpell();
    });

    private void addSpell() {
        DialogText dialogText = new DialogText("spell properties : ");
        DialogBox dialogBox = new DialogBox();
        NormalField spellId = new NormalField("spell ID");

        DialogText spellAction = new DialogText("spell action : ");
        NormalField enemyHitChange = new NormalField("enemyHitChange");
        NormalField apChange = new NormalField("ap Change");
        NormalField hpChange = new NormalField("hpChange");
        NormalField mpChange = new NormalField("mp change");
        CheckBox isPoison = new CheckBox("poison");
        CheckBox makeStun = new CheckBox("make stun");
        CheckBox disarm = new CheckBox("disarm");
        CheckBox noDisarm = new CheckBox("noDisarm");
        CheckBox noPoison = new CheckBox("noPoison");
        CheckBox noStun = new CheckBox("noStun");
        CheckBox noBadEffect = new CheckBox("noBadEffect");
        CheckBox noAttackFromWeakerOnes = new CheckBox( "noAttackFromWeakerOnes");
        CheckBox killsTarget = new CheckBox("killsTarget");
        CheckBox durable = new CheckBox("durable");
        NormalField duration = new NormalField("duration");
        NormalField delay = new NormalField("delay");

        DialogText spellTarget = new DialogText("target : ");
        CheckBox isRelatedToCardOwnerPosition = new CheckBox("is Related To Card Owner Position?");
        CheckBox isForAroundOwnHero= new CheckBox("is For Around Own Hero?");
        NormalField row = new NormalField("dimension(row)?");
        NormalField column = new NormalField("dimension(column)?");
        CheckBox isRandom = new CheckBox("isRandom?");
        CheckBox own = new CheckBox("is for own?");
        CheckBox enemy = new CheckBox("is for enemy?");
        CheckBox cell = new CheckBox("is for cell?");
        CheckBox hero = new CheckBox("is for hero?");
        CheckBox minion = new CheckBox("is for minion?");
        CheckBox melee = new CheckBox("is for melee?");
        CheckBox ranged = new CheckBox("is for ranged?");
        CheckBox hybrid = new CheckBox("is for hybrid?");
        CheckBox isForDeckCards = new CheckBox("is for isForDeckCards?");

        DialogText availab = new DialogText("availability Type : ");
        CheckBox onPut = new CheckBox("onPut");
        CheckBox onAttack = new CheckBox("onAttack");
        CheckBox onDeath = new CheckBox("onDeath");
        CheckBox continuous = new CheckBox("continuous");
        CheckBox specialPower = new CheckBox("specialPower");
        CheckBox onStart = new CheckBox("onStart");

        NormalField collDown = new NormalField("collDown");
        NormalField manaPoint = new NormalField("mana point");
        dialogBox.getChildren().addAll(dialogText,spellId,
                spellAction , new ScrollPane(new VBox(enemyHitChange,apChange,hpChange,mpChange,isPoison,makeStun,disarm,noDisarm,noPoison,noStun,noBadEffect,noAttackFromWeakerOnes,killsTarget,durable,duration,delay))
                ,spellTarget,new ScrollPane(new VBox(isRelatedToCardOwnerPosition,isForAroundOwnHero,row,column,isRandom,own,enemy,cell,hero,minion,melee,ranged,hybrid,isForDeckCards)),
                availab , new ScrollPane(new VBox(onPut,onAttack,onDeath,continuous,specialPower,onStart)),
                collDown , manaPoint

        );
        DialogContainer dialogContainer = new DialogContainer(root,dialogBox);
        dialogBox.makeButton("add",event1 -> {
            SpellAction spellAction1 = new SpellAction(Integer.parseInt(enemyHitChange.getText()), Integer.parseInt(apChange.getText()), Integer.parseInt(hpChange.getText()), Integer.parseInt(mpChange.getText()), isPoison.isSelected(), makeStun.isSelected(), disarm.isSelected(), noDisarm.isSelected(), noPoison.isSelected(), noStun.isSelected(), noBadEffect.isSelected(), noAttackFromWeakerOnes.isSelected(), killsTarget.isSelected(),  durable.isSelected(), Integer.parseInt(duration.getText()), Integer.parseInt(delay.getText()));

            Target target = new Target(isRelatedToCardOwnerPosition.isSelected(), isForAroundOwnHero.isSelected(),
                    new Position(Integer.parseInt(row.getText()), Integer.parseInt(column.getText())),
                            isRandom.isSelected(), new Owner(own.isSelected(), enemy.isSelected()),new TargetCardType(cell.isSelected(), hero.isSelected(), minion.isSelected(), false), new CardAttackType(melee.isSelected(), ranged.isSelected(), hybrid.isSelected()), isForDeckCards.isSelected());

            AvailabilityType availabilityType = new AvailabilityType(onPut.isSelected(), onAttack.isSelected(), onDeath.isSelected(), continuous.isSelected(), specialPower.isSelected(), onStart.isSelected());
            spells.add(new Spell(spellId.getText(),spellAction1,target,availabilityType, Integer.parseInt(collDown.getText()),Integer.parseInt(manaPoint.getText())));
        });


    }

    private  final EventHandler<? super MouseEvent> CREATE = event -> CustomCardController.getInstance().createCard(
            new Card(name.getText(),description.getText(),spriteName.getText(),cardTypeSpinner.getValue(),spells,
                    defualtAP.getValue(),defualtHP.getValue(),manaPoint.getValue(),Integer.parseInt(price.getText()),
                    attackTypeSpinner.getValue(),range.getValue(),hasCombo.isSelected())
    );



    private CustomCardMakerMenu(String backgroundUrl, EventHandler<? super MouseEvent> backEvent) throws FileNotFoundException {
        root.setBackground(ROOT_BACKGROUND);
        BorderPane background = BackgroundMaker.getPlayBackground(backgroundUrl);
        BackButton backButton = new BackButton(backEvent);

        AnchorPane sceneContents = new AnchorPane(background, backButton);

        root.getChildren().addAll(sceneContents);
    }

    public static CustomCardMakerMenu getInstance() {
        if (menu == null) {
            try {
                menu = new CustomCardMakerMenu( BACKGROUND_URL, BACK_EVENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
