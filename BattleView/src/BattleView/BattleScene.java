package BattleView;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.comperessedData.CompressedGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleScene {
    private final Controller controller;
    private final CompressedGame game;
    private final Scene scene;
    private final Group root;
    private final MapBox mapBox;
    private final HandBox handBox;
    private final PlayerBox playerBox;

    public BattleScene(Controller controller, CompressedGame game) throws Exception {
        this.controller = controller;
        this.game = game;
        root = new Group();
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        addBackGround("battlemap6_middleground@2x");
        handBox = new HandBox(controller, game.getPlayerOne(), Constants.SCREEN_WIDTH * 0.1,
                Constants.SCREEN_HEIGHT * 0.8);
        root.getChildren().add(handBox.getGroup());
        mapBox = new MapBox(controller, game.getGameMap(), handBox, Constants.MAP_X, Constants.MAP_Y);
        root.getChildren().add(mapBox.getMapGroup());
        playerBox = new PlayerBox(controller, game.getPlayerOne(), game.getPlayerTwo());
        root.getChildren().add(playerBox.getGroup());
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

    public Scene getScene() {
        return scene;
    }
}
