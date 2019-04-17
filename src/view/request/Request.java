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
	
	public boolean handleRequset(Client client, Server server) throws InputException {
		if (client.getCurrentMenu().equals(AccountMenu.getInstance())) {
			accountMenuHandleRequest(client, server);
		} else if (client.getCurrentMenu().equals(BattleMenu.getInstance())) {
			battleRequestHandleRequest(client, server);
		} else if (client.getCurrentMenu().equals(CollectionMenu.getInstance())) {
		
		} else if (client.getCurrentMenu().equals(CustomGameMenu.getInstance())) {
		
		} else if (client.getCurrentMenu().equals(MainMenu.getInstance())) {
		
		} else if (client.getCurrentMenu().equals(MultiPlayerMenu.getInstance())) {
		
		} else if (client.getCurrentMenu().equals(Shop.getInstance())) {
		
		} else if (client.getCurrentMenu().equals(SinglePlayerMenu.getInstance())) {
		
		}
		return false;
	}
	
	private void battleRequestHandleRequest(Client client, Server server) {
		BattleMenu battleMenu = BattleMenu.getInstance();
		if (RequestType.SINGLE_PLAYER.setMatcher(command).find()) {
			battleMenu.moveToSinglePlayerMenu(client);
		} else if (RequestType.MULTI_PLAYER.setMatcher(command).find()) {
			battleMenu.moveToMultiPlayerMenu(client, server);
		}
	}
	
	private void accountMenuHandleRequest(Client client, Server server) throws InputException {
		AccountMenu accountMenu = AccountMenu.getInstance();
		if (RequestType.CREATE_ACCOUNT.setMatcher(command).find()) {
			String userName = RequestType.CREATE_ACCOUNT.getMatcher().group(1);
			this.getNewCommand();
			accountMenu.register(client, server, userName, command);
		} else if (RequestType.LOGIN.setMatcher(command).find()) {
			String userName = RequestType.LOGIN.getMatcher().group(1);
			this.getNewCommand();
			accountMenu.login(client, server, userName, command);
		} else if (RequestType.SHOW_LEADER_BOARD.setMatcher(command).find()) {
			accountMenu.showLeaderBoard(client, server);
		} else if (RequestType.SAVE.setMatcher(command).find()) {
			accountMenu.save(client, server);
		} else if (RequestType.LOGOUT.setMatcher(command).find()) {
			accountMenu.logout(client, server);
		} else if (RequestType.HELP.setMatcher(command).find()) {
			accountMenu.help(client, server);
		} else
			throw new InputException("invalid command");
	}
}
