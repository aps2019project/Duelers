package server.models.exceptions;

public class ServerException extends LogicException {
    public ServerException(String message) {
        super(message);
    }
}