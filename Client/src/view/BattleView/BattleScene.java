package view.BattleView;

import controller.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;
import models.game.GameActions;
import models.game.map.Position;
import view.Show;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleScene extends Show {
    private static Media backgroundMusic = new Media(
            new File("resources/music/battle.m4a").toURI().toString()
    );
    private final GameActions controller;
    private final CompressedGame game;
    private final MapBox mapBox;
    private final HandBox handBox;
    private final PlayerBox playerBox;
    private final int myPlayerNumber;

    public BattleScene(GameActions controller, CompressedGame game, int myPlayerNumber, String mapName) throws Exception {
        this.controller = controller;
        this.game = game;
        this.myPlayerNumber = myPlayerNumber;
        addBackGround(mapName);
        CompressedPlayer myPlayer;
        if (myPlayerNumber == 1)
            myPlayer = game.getPlayerOne();
        else
            myPlayer = game.getPlayerTwo();
        handBox = new HandBox(this, myPlayer, Constants.HAND_X, Constants.HAND_Y);
        playerBox = new PlayerBox(this, game);
        mapBox = new MapBox(this, game.getGameMap(), Constants.MAP_X, Constants.MAP_Y);

        root.getChildren().addAll(mapBox.getMapPane(), playerBox.getGroup(), handBox.getGroup());
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

    public void attack(String cardId, Position position) {
        mapBox.showAttack(cardId, position.getColumn());
    }

    public void defend(String cardId, Position position) {
        mapBox.showDefend(cardId, position.getColumn());
    }

    public void spell(String spriteName, Position position) {
        mapBox.showSpell(spriteName, position.getRow(), position.getColumn());
    }

    @Override
    public void show() {
        super.show();
        GraphicalUserInterface.getInstance().setBackgroundMusic(backgroundMusic);
    }

    @Override
    public void showError(String message, EventHandler<? super MouseEvent> event) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showError(String message, String buttonText, EventHandler<? super MouseEvent> event) {

    }

    @Override
    public void showError(String message, String buttonText) {

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
}
