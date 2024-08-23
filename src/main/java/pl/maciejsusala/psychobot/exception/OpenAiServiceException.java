package pl.maciejsusala.psychobot.exception;

public class OpenAiServiceException extends ApplicationException {

    public OpenAiServiceException(String message) {
        super(message);
    }

    public OpenAiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}