package controller;

import client.Client;
import client.models.menus.AccountMenu;
import client.view.View;
import client.view.request.ExitCommand;
import client.view.request.InputException;
import client.view.request.Request;
import server.Server;

import java.util.ArrayList;

public class Controller {
    private static Controller mainController;
    private static Client currentClient;
    private View view = View.getInstance();
    private ArrayList<Client> clients = new ArrayList<>();
    private Server server = Server.getInstance();
    private Client mainClient;
    private Client otherClient;

    public static void changeTurn() {
        if (mainController.mainClient.equals(currentClient)) {
            currentClient = mainController.otherClient;
        } else {
            currentClient = mainController.mainClient;
        }
        currentClient.receiveMessages();
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public void main() {
        do {
            Request request = new Request();
            request.getNewCommand();
            try {
                request.handleRequest(currentClient, server.getServerName());
            } catch (InputException e) {
                view.showError(e);
            } catch (ExitCommand e) {
                break;
            }
        } while (true);
    }

    public void preProcess() {
        server.start();
        mainController = this;
        clients.add(
                new Client("mainClient", Server.getInstance())
        );
        clients.add(
                new Client("otherClient", Server.getInstance())
        );
        mainClient = clients.get(0);
        otherClient = clients.get(1);
        currentClient = mainClient;
        server.addClient(mainClient);
        server.addClient(otherClient);
        mainClient.setCurrentMenu(AccountMenu.getInstance());
    }
}
