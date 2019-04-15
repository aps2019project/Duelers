package controllers;

import models.menus.AccountMenu;
import view.View;
import view.request.InputException;
import view.request.Request;
import java.util.ArrayList;
import java.util.Scanner;

public class Organizer {
    View view = View.getInstance();
    private Scanner input;
    private ArrayList<Client> clients = new ArrayList<>();
    private Server server;
    private Client thisClient;
    public void main() {
        do{
            Request request = new Request();
            request.getNewCommand();
            try {
                request.handleRequset(thisClient,server);
            }catch (InputException e){
                view.printError(e);
            }
        }while (true);
    }
    
    public String request(String sender, String text) {
        return "";
    }
    
    public void preProcess() {
        server = new Server("server");
        clients.add(new Client(java.lang.System.getProperty("user.name"), AccountMenu.getInstance()));
        thisClient = clients.get(0);
    }
    
}