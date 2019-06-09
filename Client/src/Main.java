import controller.Client;
import controller.GraphicalUserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        new Thread(() -> {
            Client client = Client.getInstance();
            while (true) {
                try {
                    client.connect();
                } catch (IOException e) {
                    client.disconnected();
                }
            }
        }).start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GraphicalUserInterface.getInstance().start(stage);
    }
}
