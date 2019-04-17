package models.menus;

import controllers.Client;
import controllers.Server;
import view.View;

public class BattleMenu extends Menu {
    private static final BattleMenu BATTLE_MENU = new BattleMenu();

    private BattleMenu() {

    }

    public static BattleMenu getInstance() {
        return BATTLE_MENU;
    }

    public void moveToSinglePlayerMenu(Client client) {
    
    }
    
    public void moveToMultiPlayerMenu(Client client, Server server) {
        View.getInstance().printUsersList();
        
    }
}