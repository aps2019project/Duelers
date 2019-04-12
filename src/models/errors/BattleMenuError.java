package models.errors;

public enum BattleMenuError implements ErrorType {
    INVALID_SELECTED_DECK(""),
    INVALID_OPP_SELECTED_DECK("");

    private String message;


    BattleMenuError(String message) {

    }

    public String getMessage() {
        return this.message;
    }

}