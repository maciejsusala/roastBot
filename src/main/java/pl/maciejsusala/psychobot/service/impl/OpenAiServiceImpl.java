package pl.maciejsusala.psychobot.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.maciejsusala.psychobot.dto.FormDataDTO;
import pl.maciejsusala.psychobot.dto.HeaderResponseDTO;
import pl.maciejsusala.psychobot.service.OpenAiServiceInterface;

import java.util.ArrayList;
import java.util.List;


@Service
public class OpenAiServiceImpl implements OpenAiServiceInterface {

    private final OpenAiService openAiService;

    public OpenAiServiceImpl(@Value("${OPENAI_KEY}") String openAiKey) {
        this.openAiService = new OpenAiService(openAiKey);
    }


    @Override
    public HeaderResponseDTO generateHeaders(FormDataDTO formData) {
        List<String> prompts = getStrings(formData.formField1(), formData.formField2());
        List<String> headers = generateHeadersFromPrompts(prompts);
        return new HeaderResponseDTO(headers);
    }

    //TODO zmniejszyć ilość generowanych odpowiedzi do jednej
    private static @NotNull List<String> getStrings(String formField1, String formField2) {
            String promptPart1 = "Roast a user who shares a PROBLEM and REASON they can't fix this problem with you: /n " +
                    "PROBLEM = ";
        String promptPart2 = " REASON = ";
        String promptPart3 = " Now roast the user in one sarcastic text with a lot of dark humor. Don’t be afraid to offend them.";
        List<String> prompts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            prompts.add(promptPart1 + formField1 + promptPart2 + formField2 + promptPart3);
        }
        return prompts;
    }

    public List<String> generateHeadersFromPrompts(List<String> prompts) {
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