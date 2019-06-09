import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import models.comperessedData.CompressedGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class BattleScene {
    private Controller controller;
    private CompressedGame game;
    private Scene scene;
    private Group root;
    private final Polygon[][] cells = new Polygon[5][8];
    private final double[][] cellsX = new double[5][8];
    private final double[][] cellsY = new double[5][8];

    public BattleScene(Controller controller,CompressedGame game) throws Exception {
        this.controller=controller;
        this.game=game;
        root = new Group();
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        addBackGround("resources/battlemap6_middleground.png");
        makePolygons();
        addCircles();
        addTroops();
    }

    private void addBackGround(String address) {
        try {
            Image image = new Image(new FileInputStream(address));
            ImageView backGround = new ImageView(image);
            backGround.setFitWidth(Constants.SCREEN_WIDTH);
            backGround.setFitHeight(Constants.SCREEN_HEIGHT);
            root.getChildren().add(backGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makePolygons() {
        for (int j = 0; j < 5; j++) {
            double upperWidth = (j * Constants.MAP_DOWNER_WIDTH + (6 - j) * Constants.MAP_UPPER_WIDTH) / 6;
            double downerWidth = ((j + 1) * Constants.MAP_DOWNER_WIDTH + (6 - (j + 1)) * Constants.MAP_UPPER_WIDTH) / 6;
            double upperY = Constants.MAP_HEIGHT * (upperWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH) + Constants.MAP_Y;
            double downerY = Constants.MAP_HEIGHT * (downerWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH) + Constants.MAP_Y;
            for (int i = 0; i < 8; i++) {
                double x1 = Constants.MAP_X + (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + i * upperWidth / 8;
                double x2 = Constants.MAP_X + (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + (i + 1) * upperWidth / 8;
                double x3 = Constants.MAP_X + (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + (i + 1) * downerWidth / 8;
                double x4 = Constants.MAP_X + (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + i * downerWidth / 8;
                cells[j][i] = new Polygon(x1 + 1, upperY + 1, x2 - 1, upperY + 1, x3 - 1, downerY - 1, x4 + 1, downerY - 1);
                cells[j][i].setFill(Color.DARKGREEN);
                cells[j][i].setOpacity(0.5);
                final int I = i, J = j;
                cells[j][i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cells[J][I].setFill(Color.DARKBLUE);
                    }
                });
                cells[j][i].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cells[J][I].setFill(Color.DARKGREEN);
                    }
                });
                root.getChildren().add(cells[j][i]);
                cellsX[j][i] = (x1 + x2 + x3 + x4) / 4;
                cellsY[j][i] = (upperY + downerY) / 2;
            }
        }
    }

    private void addCircles() {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 8; i++) {
                root.getChildren().add(new Circle(cellsX[j][i], cellsY[j][i], 5));
            }
        }
    }

    private void addTroops() throws Exception {
        TroopAnimation troopAnimation1 = new TroopAnimation(root, cellsX, cellsY, "boss_malyk", 2, 2);
        TroopAnimation troopAnimation2 = new TroopAnimation(root, cellsX, cellsY, "boss_kron", 4, 4);

        Button button1 = new Button("KILL");
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.setAction(ACTION.DEATH);
                troopAnimation2.setAction(ACTION.DEATH);
            }
        });
        button1.setLayoutX(20);
        button1.setLayoutY(20);
        root.getChildren().add(button1);

        Button button2 = new Button("IDLE");
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.setAction(ACTION.IDLE);
                troopAnimation2.setAction(ACTION.IDLE);
            }
        });
        button2.setLayoutX(20);
        button2.setLayoutY(120);
        root.getChildren().add(button2);

        Button button3 = new Button("ATTACK");
        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.setAction(ACTION.ATTACK);
                troopAnimation2.setAction(ACTION.ATTACK);
            }
        });
        button3.setLayoutX(20);
        button3.setLayoutY(220);
        root.getChildren().add(button3);

        Button button4 = new Button("BREATHING");
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.setAction(ACTION.BREATHING);
                troopAnimation2.setAction(ACTION.BREATHING);
            }
        });
        button4.setLayoutX(20);
        button4.setLayoutY(320);
        root.getChildren().add(button4);

        Button button5 = new Button("RUN");
        button5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Random random = new Random();
                troopAnimation1.moveTo(random.nextInt(5), random.nextInt(8));
                troopAnimation2.moveTo(random.nextInt(5), random.nextInt(8));
            }
        });
        button5.setLayoutX(20);
        button5.setLayoutY(420);
        root.getChildren().add(button5);

        Button button6 = new Button("HIT");
        button6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.setAction(ACTION.HIT);
                troopAnimation2.setAction(ACTION.HIT);
            }
        });
        button6.setLayoutX(20);
        button6.setLayoutY(520);
        root.getChildren().add(button6);
    }

    public Scene getScene() {
        return scene;
    }
}
