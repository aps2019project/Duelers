package client.models.menus;

import client.Client;

public interface Menu {
    void exit(Client client);
    void showHelp();
}