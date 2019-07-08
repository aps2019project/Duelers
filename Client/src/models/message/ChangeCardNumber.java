package models.message;

class ChangeCardNumber {
    private final String cardName;
    private final int number;

    ChangeCardNumber(String cardName, int number) {
        this.cardName = cardName;
        this.number = number;
    }
}
