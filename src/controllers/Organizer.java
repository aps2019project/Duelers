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
    private ArrayList<Client> clients;
    private Server server;
    private Client thisClient;
    public void main() {
        do{
            Request request = new Request();
            request.getNewCommand();
            try {
                request.isValid(thisClient);
            }catch (InputException e){
                break;
            }
        }while (true);
    }
    public String request(String sender, String text) {
        return "";
    }
    
    public void preProcess() {
        clients.add(new Client(java.lang.System.getProperty("user.name"), AccountMenu.getInstance()));
        thisClient = clients.get(0);
    }
}