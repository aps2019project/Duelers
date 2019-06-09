package BattleView;

import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new BattleScene(this, null).getScene());
        primaryStage.show();
    }

    public void sendMessage(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
