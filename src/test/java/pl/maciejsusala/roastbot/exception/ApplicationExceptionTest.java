package pl.maciejsusala.roastbot.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationExceptionTest {

    @Test
    void testApplicationExceptionWithMessage() {
        // when
        String message = "Test message";
        ApplicationException exception = new ApplicationException(message);
        // then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testApplicationExceptionWithMessageAndCause() {
        //when
        String message = "Test message";
        Throwable cause = new Throwable("Cause message");
        ApplicationException exception = new ApplicationException(message, cause);
        // then
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}