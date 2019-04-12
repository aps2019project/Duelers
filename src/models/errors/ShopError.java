package models.errors;

public enum ShopError implements ErrorType {
    INVALID_CARD_IN_SHOP,
    INSUFFICIENT_MONEY,
    FULL_OF_ITEM_COLLECTION;

    private String message;


    public void CollectionMenuError(String message) {

    }

    public String getMessage() {
        return this.message;
    }

}