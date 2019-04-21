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
    private View view = View.getInstance();
    private ArrayList<Client> clients = new ArrayList<>();
    private Server server = Server.getInstance();
    private Client thisClient;

    public void main() {
        do {
            Request request = new Request();
            request.getNewCommand();
            try {
                request.handleRequest(thisClient, server.getServerName());
            }catch (InputException e){
                view.showError(e);
            }catch (ExitCommand e){
                break;
            }
        } while (true);
    }

    public void preProcess() {
        clients.add(
                new Client(java.lang.System.getProperty("user.name"), AccountMenu.getInstance(), Server.getInstance())
        );
        thisClient = clients.get(0);
    }
}
