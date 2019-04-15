package view.request;

import controllers.Client;

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
	
	public boolean isValid(Client client) throws InputException {
		return false;
	}
}
