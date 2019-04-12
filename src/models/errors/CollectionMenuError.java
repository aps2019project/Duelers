package models.errors;

public enum CollectionMenuError implements ErrorType{
	REPETITIOUS_DECK_NAME(""),
	INVALID_DECK(""),
	INVALID_CARD_IN_COLLECTION(""),
	ALREADY_IN_DECK(""),
	FULL_DECK(""),
	HERO_CHOSEN_FOR_DECK(""),
	INVALID_CARD_IN_DECK("");

	private String message;


	CollectionMenuError(String message) {

}

	public String getMessage() {
		return this.message;
	}

}