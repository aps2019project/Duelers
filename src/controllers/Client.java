package controllers;

import models.account.Account;
import models.card.Card;
import models.card.Deck;
import models.game.Game;
import models.map.Position;
import models.menus.Menu;
import models.message.Message;

import java.util.ArrayList;

public class Client {
	private String clientName;
	private Account account;
	private ArrayList<Message> sendingMessages = new ArrayList<>();
	private ArrayList<Message> receivingMessages = new ArrayList<>();
	private Game game;
	private ArrayList<Deck> customDecks = new ArrayList<>();
	private ArrayList<Account> leaderBoard = new ArrayList<>();
	private Menu currentMenu;
	private Card selected;
	private ArrayList<Position> positions = new ArrayList<>();
	private boolean userNameIsValid;
	private boolean passwordIsValid;
	
	public boolean isPasswordIsValid() {
		return passwordIsValid;
	}
	
	public void setPasswordIsValid(boolean passwordIsValid) {
		this.passwordIsValid = passwordIsValid;
	}
	
	public boolean isUserNameIsValid() {
		return userNameIsValid;
	}
	
	public void setUserNameIsValid(boolean userNameIsValid) {
		this.userNameIsValid = userNameIsValid;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public Client(String clientName, Menu currentMenu) {
		this.clientName = clientName;
		this.currentMenu = currentMenu;
	}
	
	public Menu getCurrentMenu() {
		return currentMenu;
	}
	
	public void addToSendingMessages(Message message) {
	
	}
	
	public void addToReceivings(String message) {
	
	}
	
	public void receiveMessages() {
	
	}
	
	public void sendMessages(Server server) {
		for(Message message:sendingMessages){
			server.addToReceivingMessages(message);
		}
		sendingMessages.clear();
		server.receiveMessages();
	}
	
	public void gameUpdate(String[] message) {
	
	}
	
	public void input(String text) {
	
	}
	
	public void outPut(String text) {
	
	}
	
	public void getGameCopy(String fileName) {
	
	}
	
	public void getShopCopy(String fileName) {
	
	}
	
	public void getAccountCopy(String fileName) {
	
	}
	
	public void getStoriesCopy(String fileName) {
	
	}
	
	public void getCustomDecksCopy(String fileName) {
	
	}
	
	public void getLeaderBoardCopy(String fileName) {
	
	}
	
	public void getPositionList(String fileName) {
	
	}
}