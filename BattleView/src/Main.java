import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    private Polygon[][] cells = new Polygon[5][8];
    private double[][] cellsX = new double[5][8];
    private double[][] cellsY = new double[5][8];

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        addBackGround(root, "resources/battlemap6_middleground.png");
        makePolygons(root);
        addTroops(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBackGround(Group root, String address) {
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

    private void makePolygons(Group root) {
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

    private void addTroops(Group root) throws Exception {
        Image heroImage = new Image(new FileInputStream("resources/boss_cindera.png"));
        ImageView heroView = new ImageView(heroImage);
        heroView.setScaleX(1);
        heroView.setScaleY(1);
        heroView.setX(cellsX[2][2]);
        heroView.setY(cellsY[2][2]);

        root.getChildren().add(heroView);

        SpriteAnimation animation = new SpriteAnimation(
                heroView, 100, 7, 0, 0, 131, 131);
        animation.play(69, 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
