package pl.maciejsusala.roastbot.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;
import pl.maciejsusala.roastbot.service.OpenAiServiceInterface;

import java.util.List;


@Service
public class OpenAiServiceImpl implements OpenAiServiceInterface {

    private final OpenAiService openAiService;

    public OpenAiServiceImpl(@Value("${OPENAI_KEY}") String openAiKey) {
        this.openAiService = new OpenAiService(openAiKey);
    }


    @Override
    public RoastResponseDTO generateRoast(FormDataDTO formData) {
        String prompt = getPrompt(formData.formField1(), formData.formField2());
        String response = generateRoastFromPrompt(prompt);
        return new RoastResponseDTO(response);
    }

    private static @NotNull String getPrompt(String formField1, String formField2) {
        String promptPart1 = "Roast a user who shares a PROBLEM and REASON they can't fix this problem with you: /n " +
                "PROBLEM = ";
        String promptPart2 = " REASON = ";
        String promptPart3 = " Now roast the user in one sarcastic text with a lot of dark humor. Don’t be afraid to offend them.";
        return (promptPart1 + formField1 + promptPart2 + formField2 + promptPart3);
    }

    public String generateRoastFromPrompt(String prompt) {

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", prompt)))
                .model("gpt-4o-mini")
                .maxTokens(100)
                .temperature(0.7)
                .n(1)
                .build();
        ChatCompletionChoice choice = openAiService.createChatCompletion(completionRequest).getChoices().getFirst();
        return choice.getMessage().getContent();

    }
}