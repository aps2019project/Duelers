import controller.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Client client = Client.getInstance();
        while (true) {
            try {
                client.connect();
            } catch (IOException e) {
                client.disconnected();
            }
        }
    }
}
