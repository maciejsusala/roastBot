package pl.maciejsusala.psychobot.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderResponseDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenHeadersAreValid_thenNoConstraintViolations() {
        HeaderResponseDTO headerResponse = new HeaderResponseDTO(List.of("Header 1", "Header 2"));
        Set<jakarta.validation.ConstraintViolation<HeaderResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(0, violations.size());
    }

    @Test
    void whenHeadersAreNull_thenOneConstraintViolation() {
        HeaderResponseDTO headerResponse = new HeaderResponseDTO(null);
        Set<jakarta.validation.ConstraintViolation<HeaderResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(2, violations.size());
    }

    @Test
    void whenHeadersAreEmpty_thenNoConstraintViolations() {
        HeaderResponseDTO headerResponse = new HeaderResponseDTO(List.of());
        Set<jakarta.validation.ConstraintViolation<HeaderResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(1, violations.size());
    }
}