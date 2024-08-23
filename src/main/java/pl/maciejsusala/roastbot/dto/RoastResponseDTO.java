package pl.maciejsusala.roastbot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RoastResponseDTO(
        @NotNull(message = "Error: Header generation failed")
        @NotEmpty(message = "Error: Header generation failed")
        String roast
) {
}