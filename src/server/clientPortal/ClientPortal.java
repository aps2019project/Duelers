package server.clientPortal;

import client.Client;
import server.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.HashMap;

public class ClientPortal extends Thread {
    private static ClientPortal ourInstance = new ClientPortal();
    private static final int PORT = 8888;
    private HashMap<String, Formatter> clients = new HashMap<>();

    public static ClientPortal getInstance() {
        return ourInstance;
    }

    private ClientPortal() {
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientListener clientListener = new ClientListener(socket);
                clientListener.setDaemon(true);
                clientListener.start();
            }
        } catch (Exception e) {
            Server.getInstance().serverPrint("Error Making ServerSocket!");
            System.exit(-1);
        }
    }

    public boolean hasThisClient(String clientName) {
        return clients.containsKey(clientName);
    }

    public void addClient(String name, Formatter formatter) {//TODO:add remove client
        clients.put(name, formatter);
    }

    void addMessage(String clientName, String message) {
        Server.getInstance().addToReceivingMessages(message);
    }

    public void sendMessage(String clientName, String message) {
        if (clients.containsKey(clientName)) {
            clients.get(clientName).format(message);
            clients.get(clientName).flush();
        } else {
            Server.getInstance().serverPrint("Client Not Found!");
        }
    }
}
