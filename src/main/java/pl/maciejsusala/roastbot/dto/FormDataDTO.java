package pl.maciejsusala.roastbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormDataDTO(
        @NotNull(message = "formField1 cannot be null")
        @NotBlank(message = "formField1 cannot be blank")
        String formField1,

        @NotNull(message = "formField2 cannot be null")
        @NotBlank(message = "formField2 cannot be blank")
        String formField2
) {
}