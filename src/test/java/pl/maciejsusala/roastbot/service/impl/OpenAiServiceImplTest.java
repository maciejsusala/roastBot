package pl.maciejsusala.roastbot.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.maciejsusala.roastbot.dto.FormDataDTO;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OpenAiServiceImplTest {

    @Mock
    private OpenAiService openAiService;
    private OpenAiServiceImpl openAiServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        openAiServiceImpl = new OpenAiServiceImpl("superSecretKey");

        Field field = OpenAiServiceImpl.class.getDeclaredField("openAiService");
        field.setAccessible(true);
        field.set(openAiServiceImpl, openAiService);
    }

    @Test
    void generateRoast() {
        FormDataDTO formData = new FormDataDTO("Problem", "Reason");
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "prompt"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice));

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);


        String roast = openAiServiceImpl.generateRoastFromPrompt("prompt");

        assertEquals("Generated Roast", roast);
    }

    @Test
    void generateRoastFromPrompt() {
        String prompt = "prompt";
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated Header"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice)); // Mocking single choice

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);

        String roast = openAiServiceImpl.generateRoastFromPrompt(prompt);

        assertEquals("Generated roast", roast);
    }
}