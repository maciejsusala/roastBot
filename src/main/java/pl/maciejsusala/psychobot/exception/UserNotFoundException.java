package pl.maciejsusala.psychobot.exception;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
