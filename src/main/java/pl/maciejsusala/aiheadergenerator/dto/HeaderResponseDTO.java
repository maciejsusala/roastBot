package pl.maciejsusala.aiheadergenerator.dto;

import java.util.List;

public record HeaderResponseDTO(
        List<String> headers
) {
}