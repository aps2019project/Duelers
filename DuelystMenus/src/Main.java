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
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.show();

        System.out.println(Font.getFamilies());
    }
}
