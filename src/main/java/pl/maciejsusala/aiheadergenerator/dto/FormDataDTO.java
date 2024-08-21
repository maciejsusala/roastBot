package pl.maciejsusala.aiheadergenerator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormDataDTO(
        @NotNull(message = "formField1 cannot be null")
        @NotBlank(message = "formField1 cannot be blank")
        String formField1,

        @NotNull(message = "formField2 cannot be null")
        @NotBlank(message = "formField2 cannot be blank")
        String formField2,

        @NotNull(message = "formField3 cannot be null")
        @NotBlank(message = "formField3 cannot be blank")
        String formField3
) {
}