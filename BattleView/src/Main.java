import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 700, 700);

        Image heroImage = new Image(new FileInputStream("resources/boss_cindera.png"));
        ImageView heroView = new ImageView(heroImage);
        heroView.setScaleX(5);
        heroView.setScaleY(5);
        heroView.setX(250);
        heroView.setY(250);

        root.getChildren().add(heroView);

        SpriteAnimation animation = new SpriteAnimation(
                heroView, 100, 7, 0, 0, 131, 131);
        animation.play(69, 0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
