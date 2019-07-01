package server.clientPortal;

import server.Server;
import server.clientPortal.models.message.Message;
import server.detaCenter.DataCenter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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
        Server.getInstance().serverPrint("Starting ClientPortal...");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientListener clientListener = new ClientListener(socket);
                clientListener.setDaemon(true);
                clientListener.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Server.getInstance().serverPrint("Error Making ServerSocket!");
            System.exit(-1);
        }
    }

    synchronized public boolean hasThisClient(String clientName) {
        return clients.containsKey(clientName);
    }

    synchronized void addClient(String name, Formatter formatter) {//TODO:add remove client
        clients.put(name, formatter);
        DataCenter.getInstance().putClient(name, null);
    }

    void addMessage(String clientName, String message) {
        Server.getInstance().addToReceivingMessages(Message.convertJsonToMessage(message));
    }

    synchronized public void sendMessage(String clientName, String message) {//TODO:Change Synchronization
        if (clients.containsKey(clientName)) {
            clients.get(clientName).format(message + "\n");
            clients.get(clientName).flush();
        } else {
            Server.getInstance().serverPrint("Client Not Found!");
        }
    }

    public Set<Map.Entry<String, Formatter>> getClients() {
        return Collections.unmodifiableSet(clients.entrySet());
    }

    void removeClient(String clientName) {
        clients.remove(clientName);
    }
}
