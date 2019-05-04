package client.models.message;

public class AccountFields {
    String username;
    String password;

    public AccountFields(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountFields(String username) {
        this.username = username;
    }
}
