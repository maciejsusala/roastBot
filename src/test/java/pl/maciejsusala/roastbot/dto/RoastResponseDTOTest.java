package pl.maciejsusala.roastbot.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoastResponseDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenHeadersAreValid_thenNoConstraintViolations() {
        RoastResponseDTO headerResponse = new RoastResponseDTO("Roast");
        Set<jakarta.validation.ConstraintViolation<RoastResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(0, violations.size());
    }

    @Test
    void whenHeadersAreNull_thenOneConstraintViolation() {
        RoastResponseDTO headerResponse = new RoastResponseDTO(null);
        Set<jakarta.validation.ConstraintViolation<RoastResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(2, violations.size());
    }

    @Test
    void whenHeadersAreEmpty_thenNoConstraintViolations() {
        RoastResponseDTO headerResponse = new RoastResponseDTO("");
        Set<jakarta.validation.ConstraintViolation<RoastResponseDTO>> violations = validator.validate(headerResponse);
        assertEquals(1, violations.size());
    }
}