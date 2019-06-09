package BattleView;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.comperessedData.CompressedGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleScene {
    private Controller controller;
    private CompressedGame game;
    private Scene scene;
    private Group root;
    private MapView mapView;


    public BattleScene(Controller controller, CompressedGame game) throws Exception {
        this.controller = controller;
        this.game = game;
        root = new Group();
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        addBackGround("battlemap6_middleground@2x");
        mapView = new MapView(controller, game);
        root.getChildren().add(mapView.getMapGroup());
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
