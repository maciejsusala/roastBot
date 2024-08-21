package pl.maciejsusala.aiheadergenerator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record FormDataDTO(
        @NotNull (message = "Name cannot be null")
        @NotEmpty (message = "Name cannot be empty")
        @NotBlank (message = "Name cannot be blank")
        String formField1,

        @NotNull (message = "Name cannot be null")
        @NotEmpty (message = "Name cannot be empty")
        @NotBlank (message = "Name cannot be blank")

        String formField2,

        @NotNull (message = "Name cannot be null")
        @NotEmpty (message = "Name cannot be empty")
        @NotBlank (message = "Name cannot be blank")
        String formField3
) {
}