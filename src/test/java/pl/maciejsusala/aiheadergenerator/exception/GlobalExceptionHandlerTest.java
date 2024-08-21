package pl.maciejsusala.aiheadergenerator.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.maciejsusala.aiheadergenerator.dto.ErrorResponseDTO;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("objectName", "field1", "defaultMessage1"),
                new FieldError("objectName", "field2", "defaultMessage2")
        ));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ErrorResponseDTO response = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals("Validation error", response.message());
        assertEquals("field1: defaultMessage1, field2: defaultMessage2", response.details());
        assertEquals(2, response.validationErrors().size());
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        ErrorResponseDTO response = globalExceptionHandler.handleIllegalArgumentException(ex);

        assertEquals("Invalid input", response.message());
        assertEquals("Invalid argument", response.details());
    }

    @Test
    void handleOpenAiServiceException() {
        OpenAiServiceException ex = new OpenAiServiceException("Service error occurred");

        ErrorResponseDTO response = globalExceptionHandler.handleOpenAiServiceException(ex);

        assertEquals("Service error", response.message());
        assertEquals("Service error occurred", response.details());
    }
}