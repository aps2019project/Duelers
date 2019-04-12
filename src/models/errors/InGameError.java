package models.errors;

public enum InGameError implements ErrorType {
	INVALID_CARD_ID(""),
	INVALID_TARGET(""),
	OPP_MINION_UNAVAILABLE(""),
	CARD_CANNOT_ATTACK(""),
	INSUFFICIENT_MANNA(""),
	SPECIAL_POWER_NOT_AVAILABLE(""),
	INVALID_CARD_NAME(""),
	INVALID_TURN("");

	private String message;


	InGameError(String message) {

}

	public String getMessage() {
		return this.message;
	}

}