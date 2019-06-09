package BattleView;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import models.comperessedData.CompressedGame;

import java.util.Random;

public class MapView {
    private Controller controller;
    private CompressedGame game;
    private Group mapGroup;
    private final Polygon[][] cells = new Polygon[5][8];
    private final double[][] cellsX = new double[5][8];
    private final double[][] cellsY = new double[5][8];

    public MapView(Controller controller, CompressedGame game) throws Exception {
        this.controller = controller;
        this.game = game;
        mapGroup = new Group();
        mapGroup.setLayoutY(Constants.MAP_Y);
        mapGroup.setLayoutX(Constants.MAP_X);
        makePolygons();
        addCircles();
        addTroops();
    }

    private void makePolygons() {
        for (int j = 0; j < 5; j++) {
            double upperWidth = (j * Constants.MAP_DOWNER_WIDTH + (6 - j) * Constants.MAP_UPPER_WIDTH) / 6;
            double downerWidth = ((j + 1) * Constants.MAP_DOWNER_WIDTH + (6 - (j + 1)) * Constants.MAP_UPPER_WIDTH) / 6;
            double upperY = Constants.MAP_HEIGHT * (upperWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH);
            double downerY = Constants.MAP_HEIGHT * (downerWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH);
            for (int i = 0; i < 8; i++) {
                double x1 = (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + i * upperWidth / 8;
                double x2 = (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + (i + 1) * upperWidth / 8;
                double x3 = (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + (i + 1) * downerWidth / 8;
                double x4 = (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + i * downerWidth / 8;
                cells[j][i] = new Polygon(x1 + Constants.SPACE_BETWEEN_CELLS / 2,
                        upperY + Constants.SPACE_BETWEEN_CELLS / 2, x2 - Constants.SPACE_BETWEEN_CELLS / 2,
                        upperY + Constants.SPACE_BETWEEN_CELLS / 2, x3 - Constants.SPACE_BETWEEN_CELLS / 2,
                        downerY - Constants.SPACE_BETWEEN_CELLS / 2, x4 + Constants.SPACE_BETWEEN_CELLS / 2,
                        downerY - Constants.SPACE_BETWEEN_CELLS / 2);
                cells[j][i].setFill(Color.DARKGREEN);
                cells[j][i].setOpacity(Constants.CELLS_OPACITY);
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
                mapGroup.getChildren().add(cells[j][i]);
                cellsX[j][i] = (x1 + x2 + x3 + x4) / 4;
                cellsY[j][i] = (upperY + downerY) / 2;
            }
        }
    }

    private void addCircles() {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 8; i++) {
                mapGroup.getChildren().add(new Circle(cellsX[j][i], cellsY[j][i], 2));
            }
        }
    }

    private void addTroops() throws Exception {
        TroopAnimation troopAnimation1 = new TroopAnimation(mapGroup, cellsX, cellsY, "boss_malyk", 2, 2);
        TroopAnimation troopAnimation2 = new TroopAnimation(mapGroup, cellsX, cellsY, "boss_kron", 4, 4);

        Button button1 = new Button("KILL");
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.kill();
            }
        });
        button1.setLayoutX(-20);
        button1.setLayoutY(20);
        mapGroup.getChildren().add(button1);

        Button button2 = new Button("IDLE");
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.idle();
            }
        });
        button2.setLayoutX(-20);
        button2.setLayoutY(120);
        mapGroup.getChildren().add(button2);

        Button button3 = new Button("ATTACK");
        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.attack();
            }
        });
        button3.setLayoutX(-20);
        button3.setLayoutY(220);
        mapGroup.getChildren().add(button3);

        Button button4 = new Button("BREATHING");
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.breathe();
            }
        });
        button4.setLayoutX(-20);
        button4.setLayoutY(320);
        mapGroup.getChildren().add(button4);

        Button button5 = new Button("RUN");
        button5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Random random = new Random();
                troopAnimation1.moveTo(random.nextInt(5), random.nextInt(8));
            }
        });
        button5.setLayoutX(-20);
        button5.setLayoutY(420);
        mapGroup.getChildren().add(button5);

        Button button6 = new Button("HIT");
        button6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                troopAnimation1.hit();
            }
        });
        button6.setLayoutX(-20);
        button6.setLayoutY(520);
        mapGroup.getChildren().add(button6);
    }

    public Group getMapGroup() {
        return mapGroup;
    }
}
