package pl.maciejsusala.roastbot.exception;

public class DuplicateUserException extends ApplicationException {

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}