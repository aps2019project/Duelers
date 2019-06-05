import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    static void setScene(Scene scene) {
        scene.setCursor(Constants.DEFAULT_CURSOR);
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            new LoginMenu().show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stage.setTitle("DUELYST");
        stage.setResizable(false);
        stage.show();

        System.out.println(Font.getFamilies());
    }
}
