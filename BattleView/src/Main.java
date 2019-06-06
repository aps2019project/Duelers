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


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Image image = new Image(new FileInputStream("resources/battlemap6_middleground.png"));
        ImageView backGround = new ImageView(image);
        backGround.setFitWidth(Constants.SCREEN_WIDTH);
        backGround.setFitHeight(Constants.SCREEN_HEIGHT);
        root.getChildren().add(backGround);

        Polygon[][] cells = new Polygon[5][8];
        for (int j = 0; j < 5; j++) {
            double newL2 = (j * Constants.L1 + (6 - j) * Constants.L2) / 6;
            double newL1 = ((j + 1) * Constants.L1 + (6 - (j + 1)) * Constants.L2) / 6;
            double newH2 = Constants.H * (newL2 - Constants.L2) / (Constants.L1 - Constants.L2) + Constants.Y;
            double newH1 = Constants.H * (newL1 - Constants.L2) / (Constants.L1 - Constants.L2) + Constants.Y;
            for (int i = 0; i < 8; i++) {
                double x1 = Constants.X + (Constants.L1 - newL2) / 2 + i * newL2 / 8;
                double x2 = Constants.X + (Constants.L1 - newL2) / 2 + (i + 1) * newL2 / 8;
                double x3 = Constants.X + (Constants.L1 - newL1) / 2 + (i + 1) * newL1 / 8;
                double x4 = Constants.X + (Constants.L1 - newL1) / 2 + i * newL1 / 8;
                cells[j][i] = new Polygon(x1 + 1, newH2 + 1, x2 - 1, newH2 + 1, x3 - 1, newH1 - 1, x4 + 1, newH1 - 1);
                cells[j][i].setFill(Color.DARKGREEN);
                cells[j][i].setOpacity(0.5);
                final int I=i,J=j;
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
            }
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
