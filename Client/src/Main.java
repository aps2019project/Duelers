import controller.Client;
import controller.GraphicalUserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        new Thread(() -> {
            Client client = Client.getInstance();
            while (true) {
                try {
                    client.connect();
                } catch (IOException e) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ignored) {
                    }
                    client.disconnected();
                }
            }
        }).start();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GraphicalUserInterface.getInstance().start(stage);
    }
}
