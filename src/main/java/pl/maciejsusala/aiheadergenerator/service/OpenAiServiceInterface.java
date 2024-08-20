package pl.maciejsusala.aiheadergenerator.service;

import java.util.List;

// I decide to use an interface to define the contract for OpenAI service operations.
// This allows for flexibility in swapping implementations, especially useful for testing.

public interface OpenAiServiceInterface {
    List<String> createPrompts(String formField1, String formField2, String formField3);
    List<String> generateHeaders(List<String> prompts);
}