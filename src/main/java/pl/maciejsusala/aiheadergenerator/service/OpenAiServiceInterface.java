package pl.maciejsusala.aiheadergenerator.service;
import pl.maciejsusala.aiheadergenerator.dto.FormDataDTO;
import pl.maciejsusala.aiheadergenerator.dto.HeaderResponseDTO;

import java.util.List;

// I decide to use an interface to define the contract for OpenAI service operations.
// This allows for flexibility in swapping implementations.

public interface OpenAiServiceInterface {
    HeaderResponseDTO generateHeaders(FormDataDTO formData);
}