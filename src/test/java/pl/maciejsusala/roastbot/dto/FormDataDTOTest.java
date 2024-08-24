package pl.maciejsusala.roastbot.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormDataDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoConstraintViolations() {
        FormDataDTO formData = new FormDataDTO("Problem", "Reason");
        Set<jakarta.validation.ConstraintViolation<FormDataDTO>> violations = validator.validate(formData);
        assertEquals(0, violations.size());
    }

    @Test
    void whenFieldIsNull_thenOneConstraintViolation() {
        FormDataDTO formData = new FormDataDTO(null, "Reason");
        Set<jakarta.validation.ConstraintViolation<FormDataDTO>> violations = validator.validate(formData);
        assertEquals(2, violations.size());
    }

    @Test
    void whenFieldIsBlank_thenOneConstraintViolation() {
        FormDataDTO formData = new FormDataDTO("", "Reason");
        Set<jakarta.validation.ConstraintViolation<FormDataDTO>> violations = validator.validate(formData);
        assertEquals(1, violations.size());
    }
}