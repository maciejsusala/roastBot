package pl.maciejsusala.aiheadergenerator.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    public List<String> createPrompts(String formField1, String formField2, String formField3) {
        if (formField1 == null || formField2 == null || formField3 == null) {
            throw new IllegalArgumentException("Form fields cannot be null");
        }

        if(formField1.isEmpty() || formField2.isEmpty() || formField3.isEmpty()) {
            throw new IllegalArgumentException("Form fields cannot be empty");
        }
        List<String> prompts = getStrings(formField1, formField2, formField3);
        return prompts;
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

    @Override
    public List<String> generateHeaders(List<String> prompts) {
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