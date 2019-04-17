package controllers;

import models.card.Card;
import models.message.Message;
import models.account.Account;
import models.card.Deck;
import models.game.Game;
import models.game.GameType;
import models.game.Player;
import models.game.Story;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;

public class Server {
	private static Server server;
	private String serverName;
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Card> originalCards=new ArrayList<>();
	private ArrayList<OnlineClient> onlineClients = new ArrayList<>();
	private ArrayList<Game> onlineGames = new ArrayList<>();
	private ArrayList<Deck> customDecks = new ArrayList<>();
	private ArrayList<Account> leaderBoard = new ArrayList<>();
	private ArrayList<Message> sendingMessages = new ArrayList<>();
	private ArrayList<Message> receivingMessages = new ArrayList<>();
	private ArrayList<Story> stories = new ArrayList<>();
	
	public String getServerName() {
		return serverName;
	}
	
	private Server(String serverName) {
		readAccounts();
		readCustomDecks();
		readStories();
		this.serverName = serverName;
	}

	private void readAccounts(){
		//file
	}

	private void readCustomDecks(){
		//file
	}

	private void readStories(){
		//file
	}

	public static Server getInstance(){
		if(server==null)
			server=new Server("Server");
		return server;
	}
	
	public void addToSendingMessages(Message message) {
		sendingMessages.add(message);
	}
	
	public void addToReceivingMessages(Message message) {
		receivingMessages.add(message);
	}
	
	public void receiveMessages() {
		for(Message message:receivingMessages){

		}
		receivingMessages.clear();
	}
	
	public void sendMessages() {
		for(Message message:sendingMessages){

		}
		sendingMessages.clear();
	}
	
	public void newGame(GameType gameType, Player playerOne, Player playerTwo, Map map) {
		onlineGames.add(new Game(gameType, playerOne, playerTwo, map));
		//...
	}
}