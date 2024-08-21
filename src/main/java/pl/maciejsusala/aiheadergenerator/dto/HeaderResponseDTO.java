package pl.maciejsusala.aiheadergenerator.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HeaderResponseDTO(
        @NotNull(message = "Error: Header generation failed")
        @NotEmpty(message = "Error: Header generation failed")
        List<String> headers
) {
}