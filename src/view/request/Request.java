package view.request;

import controllers.Client;
import controllers.Server;
import models.menus.*;
import models.message.Message;

import java.util.Scanner;

public class Request {
	private static Scanner scanner = new Scanner(System.in);
	private String command;
	private RequestType requestType = null;
	
	public RequestType getRequestType() {
		return requestType;
	}
	
	
	public void getNewCommand() {
		this.command = scanner.nextLine();
	}
	
	public boolean handleRequset(Client client,Server server) throws InputException {
		if (client.getCurrentMenu().equals(AccountMenu.getInstance())){
			accountMenuHandleRequest(client,server);
		}else if (client.getCurrentMenu().equals(BattleMenu.getInstance())) {
		
		}else if (client.getCurrentMenu().equals(CollectionMenu.getInstance())){
		
		}else if (client.getCurrentMenu().equals(CustomGameMenu.getInstance())){
		
		}else if(client.getCurrentMenu().equals(MainMenu.getInstance())){
		
		}else if (client.getCurrentMenu().equals(MultiPlayerMenu.getInstance())){
		
		}else if (client.getCurrentMenu().equals(Shop.getInstance())){
		
		}else if (client.getCurrentMenu().equals(SinglePlayerMenu.getInstance())){
		
		}
		return false;
	}
	
	private void accountMenuHandleRequest(Client client,Server server) throws InputException {
		if (RequestType.CREATE_ACCOUNT.setMatcher(command).find()){
			String userName = RequestType.CREATE_ACCOUNT.getMatcher().group(1);
			this.getNewCommand();
			createAccount(client,server,userName,command);
		}
	}
	
	private void createAccount(Client client,Server server,String userName,String password) throws InputException{
		client.addToSendingMessages(Message.register(client.getClientName(),server.getServerName(),userName,password));
		client.sendMessages(server);
		if (!client.isValid()) {
			throw new InputException("hello");
		}
	}
}
