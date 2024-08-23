package pl.maciejsusala.psychobot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpenAiServiceExceptionTest {

    @Test
    void testOpenAiServiceExceptionWithMessage() {
        String message = "Service error occurred";
        OpenAiServiceException exception = new OpenAiServiceException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testOpenAiServiceExceptionWithMessageAndCause() {
        String message = "Service error occurred";
        Throwable cause = new Throwable("Cause message");
        OpenAiServiceException exception = new OpenAiServiceException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}