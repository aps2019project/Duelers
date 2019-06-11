import controller.Client;
import controller.GraphicalUserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Client.getInstance().makeConnection();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GraphicalUserInterface.getInstance().start(stage);
    }
}
