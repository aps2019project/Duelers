package view.BattleView;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;
import view.Show;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleScene extends Show {
    private final Controller controller;
    private final CompressedGame game;
    private final MapBox mapBox;
    private final HandBox handBox;
    private final PlayerBox playerBox;
    private final int myPlayerNumber;

    public BattleScene(Controller controller, CompressedGame game, int myPlayerNumber) throws Exception {
        this.controller = controller;
        this.game = game;
        this.myPlayerNumber = myPlayerNumber;
        addBackGround("battlemap6_middleground@2x");
        CompressedPlayer myPlayer;
        if (myPlayerNumber == 1)
            myPlayer = game.getPlayerOne();
        else
            myPlayer = game.getPlayerTwo();
        handBox = new HandBox(this, myPlayer, Constants.SCREEN_WIDTH * 0.1,
                Constants.SCREEN_HEIGHT * 0.8);
        mapBox = new MapBox(this, game.getGameMap(), Constants.MAP_X, Constants.MAP_Y);
        playerBox = new PlayerBox(this, game);
        root.getChildren().addAll(handBox.getGroup(), mapBox.getMapGroup(), playerBox.getGroup());
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

    @Override
    public void show() {

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

    public MapBox getMapBox() {
        return mapBox;
    }

    public HandBox getHandBox() {
        return handBox;
    }

    public PlayerBox getPlayerBox() {
        return playerBox;
    }

    public Controller getController() {
        return controller;
    }

    public int getMyPlayerNumber() {
        return myPlayerNumber;
    }


}
