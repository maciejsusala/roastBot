package pl.maciejsusala.aiheadergenerator.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String message,
        String details,
        LocalDateTime timestamp,
        List<String> validationErrors
) {
}