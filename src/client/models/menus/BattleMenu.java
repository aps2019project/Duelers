package client.models.menus;

import client.Client;
import client.view.View;

public class BattleMenu extends Menu {
    private static final BattleMenu BATTLE_MENU = new BattleMenu();

    private BattleMenu() {

    }

    public static BattleMenu getInstance() {
        return BATTLE_MENU;
    }

    public void moveToSinglePlayerMenu(Client client) {
    
    }
    
    public void moveToMultiPlayerMenu(Client client, String serverName) {
        View.getInstance().printUsersList(client);
        
    }
}