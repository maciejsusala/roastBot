package pl.maciejsusala.psychobot.service;
import pl.maciejsusala.psychobot.dto.FormDataDTO;
import pl.maciejsusala.psychobot.dto.HeaderResponseDTO;

// I decide to use an interface to define the contract for OpenAI service operations.
// This allows for flexibility in swapping implementations.

public interface OpenAiServiceInterface {
    HeaderResponseDTO generateHeaders(FormDataDTO formData);
}