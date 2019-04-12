package models.errors;

public enum AccountMenuError implements ErrorType{
    REPETITIOUS_USERNAME(""),
    INVALID_USERNAME(""),
    INVALID_PASSWORD("");

    private String message;

    AccountMenuError(String message) {

    }

    public String getMessage() {
        return this.message;
    }

}