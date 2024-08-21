package pl.maciejsusala.aiheadergenerator.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.maciejsusala.aiheadergenerator.dto.FormDataDTO;
import pl.maciejsusala.aiheadergenerator.dto.HeaderResponseDTO;

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
    void generateHeaders() {
        FormDataDTO formData = new FormDataDTO("goal", "objection", "product");
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated Header"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice, choice, choice)); // Mocking multiple choices

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);

        HeaderResponseDTO response = openAiServiceImpl.generateHeaders(formData);

        assertEquals(List.of("Generated Header", "Generated Header", "Generated Header"), response.headers());
    }

    @Test
    void generateHeadersFromPrompts() {
        List<String> prompts = List.of("prompt1", "prompt2", "prompt3");
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated Header"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice)); // Mocking single choice

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);

        List<String> headers = openAiServiceImpl.generateHeadersFromPrompts(prompts);

        assertEquals(List.of("Generated Header", "Generated Header", "Generated Header"), headers);
    }
}