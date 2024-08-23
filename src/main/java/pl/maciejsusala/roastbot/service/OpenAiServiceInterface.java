package pl.maciejsusala.roastbot.service;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;

// I decide to use an interface to define the contract for OpenAI service operations.
// This allows for flexibility in swapping implementations.

public interface OpenAiServiceInterface {
    RoastResponseDTO generateHeaders(FormDataDTO formData);
}