package models.menus;

import controllers.Client;
import controllers.Server;
import models.message.Message;
import view.View;
import view.request.InputException;

public class AccountMenu extends Menu {
    private static AccountMenu ACCOUNT_MENU;

    private AccountMenu() {
    }

    public static AccountMenu getInstance()
    {
        if (ACCOUNT_MENU == null) {
             ACCOUNT_MENU = new AccountMenu();
        }
        return ACCOUNT_MENU;
    }

    public void register(Client client,Server server,String userName,String password) throws InputException{
        client.addToSendingMessages(Message.makeRegisterMessage(client.getClientName(),server.getServerName(),userName,password,0));
        client.sendMessages(server);
        if (!client.isUserNameIsValid()) {
            throw new InputException("invalid UserName");
        }
    }
    
    public void login(Client client, Server server, String userName, String password) throws InputException{
        client.addToSendingMessages(Message.makeLogInMessage(client.getClientName(),server.getServerName(),userName,password,0));
        client.sendMessages(server);
        if (!client.isUserNameIsValid()) {
            throw new InputException("invalid UserName");
        }
        if (!client.isPasswordIsValid()){
            throw new InputException("password is invalid");
        }
    }

    public void updateLeaderBoard(Client client,Server server) {
		client.addToSendingMessages(Message.makeGetLeaderBoardMessage(client.getClientName(),server.getServerName(),0));
		client.sendMessages(server);
    }

    public void showLeaderBoard(Client client,Server server) {
    	updateLeaderBoard(client,server);
	    View.getInstance().printLeaderBoard(client);
    }

    public void save(Client client,Server server) {

    }
    public void help(Client client,Server server){
    
    }
    public void logout(Client client,Server server) {

    }
}