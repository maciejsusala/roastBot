package pl.maciejsusala.aiheadergenerator.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.maciejsusala.aiheadergenerator.dto.FormDataDTO;
import pl.maciejsusala.aiheadergenerator.dto.HeaderResponseDTO;
import pl.maciejsusala.aiheadergenerator.service.OpenAiServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiServiceImpl implements OpenAiServiceInterface {

    private final OpenAiService openAiService;

    public OpenAiServiceImpl(@Value("${OPENAI_KEY}") String openAiKey) {
        this.openAiService = new OpenAiService(openAiKey);
    }

    // Constructor for testing purposes
    public OpenAiServiceImpl(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public HeaderResponseDTO generateHeaders(FormDataDTO formData) {
        if (formData.formField1() == null || formData.formField2() == null || formData.formField3() == null) {
            throw new IllegalArgumentException("Form fields cannot be null");
        }

        if(formData.formField1().isEmpty() || formData.formField2().isEmpty() || formData.formField3().isEmpty()) {
            throw new IllegalArgumentException("Form fields cannot be empty");
        }

        List<String> prompts = getStrings(formData.formField1(), formData.formField2(), formData.formField3());
        List<String> headers = generateHeadersFromPrompts(prompts);
        return new HeaderResponseDTO(headers);
    }

    private static @NotNull List<String> getStrings(String formField1, String formField2, String formField3) {
        String promptPart1 = "Stwórz propozycję nagłówka, który przyciągnie uwagę odbiorcy, obiecując pokonanie OBIEKCJI i osiągnięcie CELU za pomocą PRODUKTU. Nagłówek powinien mieć nie więcej niż 15 wyrazów. CEL = ";
        String promptPart2 = " OBIEKCJA = ";
        String promptPart3 = " PRODUKT = ";
        List<String> prompts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            prompts.add(promptPart1 + formField1 + promptPart2 + formField2 + promptPart3 + formField3);
        }
        return prompts;
    }

    public List<String> generateHeadersFromPrompts(List<String> prompts) {
        if (prompts == null || prompts.isEmpty()) {
            throw new IllegalArgumentException("Prompts list cannot be null or empty");
        }
        List<String> headers = new ArrayList<>();
        for (String prompt : prompts) {
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .model("gpt-4o-mini")
                    .maxTokens(100)
                    .temperature(0.7)
                    .build();
            List<ChatCompletionChoice> choices = openAiService.createChatCompletion(completionRequest).getChoices();
            headers.add(choices.getFirst().getMessage().getContent());
        }
        return headers;
    }
}