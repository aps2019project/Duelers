package view.BattleView;

import controller.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import models.card.spell.AvailabilityType;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;
import models.game.GameActions;
import models.game.map.Position;
import view.Show;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class BattleScene extends Show {
    private static Media backgroundMusic = new Media(
            new File("resources/music/music_battlemap_vetruv.m4a").toURI().toString()
    );
    private static final Map<SpellType, String> spellSpriteNames = new HashMap();
    private final GameActions controller;
    private final CompressedGame game;
    private final MapBox mapBox;
    private final HandBox handBox;
    private final PlayerBox playerBox;
    private final int myPlayerNumber;
    private final CompressedPlayer myPlayer;
    private final CompressedPlayer oppPlayer;

    static {
        spellSpriteNames.put(SpellType.ATTACK, "fx_f4_shadownova");
        spellSpriteNames.put(SpellType.PUT, "");
        spellSpriteNames.put(SpellType.DEATH, "");
        spellSpriteNames.put(SpellType.DEFEND, "");
        spellSpriteNames.put(SpellType.CONTINUOUS, "");
        spellSpriteNames.put(SpellType.SPECIAL_POWER, "");
        spellSpriteNames.put(SpellType.DEFAULT, "");
    }

    public BattleScene(GameActions controller, CompressedGame game, int myPlayerNumber, String mapName) throws Exception {
        this.controller = controller;
        this.game = game;
        this.myPlayerNumber = myPlayerNumber;
        addBackGround(mapName);
        if (myPlayerNumber == 1) {
            myPlayer = game.getPlayerOne();
            oppPlayer = game.getPlayerTwo();
        } else {
            myPlayer = game.getPlayerTwo();
            oppPlayer = game.getPlayerOne();
        }
        handBox = new HandBox(this, myPlayer, Constants.HAND_X, Constants.HAND_Y);
        playerBox = new PlayerBox(this, game);
        mapBox = new MapBox(this, game.getGameMap(), Constants.MAP_X, Constants.MAP_Y);

        root.getChildren().addAll(mapBox.getMapGroup(), playerBox.getGroup(), handBox.getHandGroup());
    }

    private void addBackGround(String address) {
        try {
            Image image = new Image(new FileInputStream("resources/backGrounds/" + address + ".png"));
            ImageView backGround = new ImageView(image);
            backGround.setFitWidth(Constants.SCREEN_WIDTH);
            backGround.setFitHeight(Constants.SCREEN_HEIGHT);
            root.getChildren().add(backGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void attack(String cardId, String defender) {
        mapBox.showAttack(cardId, defender);
        new Thread(() -> {
            try {
                Thread.sleep(200);
                defend(defender, cardId);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    void defend(String defender, String attacker) {
        mapBox.showDefend(defender, attacker);
    }

    public void spell(AvailabilityType availabilityType, Position position) {
        mapBox.showSpell(getSpellSpriteName(availabilityType), position.getRow(), position.getColumn());
    }

    private String getSpellSpriteName(AvailabilityType availabilityType) {
        if (availabilityType.isOnAttack()) return spellSpriteNames.get(SpellType.ATTACK);
        if (availabilityType.isOnDeath()) return spellSpriteNames.get(SpellType.DEATH);
        if (availabilityType.isOnDefend()) return spellSpriteNames.get(SpellType.DEFEND);
        if (availabilityType.isSpecialPower()) return spellSpriteNames.get(SpellType.SPECIAL_POWER);
        if (availabilityType.isContinuous()) return spellSpriteNames.get(SpellType.CONTINUOUS);
        if (availabilityType.isOnPut()) return spellSpriteNames.get(SpellType.PUT);
        return spellSpriteNames.get(SpellType.DEFAULT);
    }

    @Override
    public void show() {
        super.show();
        GraphicalUserInterface.getInstance().setBackgroundMusic(backgroundMusic);
    }

    @Override
    public void showError(String message, String buttonText, EventHandler<? super MouseEvent> event) {
    }

    MapBox getMapBox() {
        return mapBox;
    }

    HandBox getHandBox() {
        return handBox;
    }

    PlayerBox getPlayerBox() {
        return playerBox;
    }

    public GameActions getController() {
        return controller;
    }

    int getMyPlayerNumber() {
        return myPlayerNumber;
    }

    boolean isMyTurn() {
        return (game.getTurnNumber() % 2 == 1 && myPlayerNumber == 1) || (game.getTurnNumber() % 2 == 0 && myPlayerNumber == 2);
    }

    public CompressedGame getGame() {
        return game;
    }

    public void showOpponentMessage(String text) {
        playerBox.showMessage(oppPlayer, text);
    }

    public void showMyMessage(String text) {
        playerBox.showMessage(myPlayer, text);
    }

    public String getMyUserName() {
        return myPlayer.getUserName();
    }

    public String getOppUserName() {
        return oppPlayer.getUserName();
    }

    private enum SpellType {
        ATTACK, PUT, DEATH, CONTINUOUS, SPECIAL_POWER, DEFEND, DEFAULT
    }
}
