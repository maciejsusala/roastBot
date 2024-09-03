package pl.maciejsusala.roastbot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoastGenerationServiceExceptionTest {

    @Test
    void testOpenAiServiceExceptionWithMessage() {
        //when
        String message = "Service error occurred";
        OpenAiServiceException exception = new OpenAiServiceException(message);

        //then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testOpenAiServiceExceptionWithMessageAndCause() {
        //when
        String message = "Service error occurred";
        Throwable cause = new Throwable("Cause message");
        OpenAiServiceException exception = new OpenAiServiceException(message, cause);

        //then
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}