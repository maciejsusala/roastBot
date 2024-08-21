package pl.maciejsusala.aiheadergenerator.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.maciejsusala.aiheadergenerator.dto.FormDataDTO;
import pl.maciejsusala.aiheadergenerator.dto.HeaderResponseDTO;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OpenAiServiceImplTest {

    @Mock
    private OpenAiService openAiService;

    @InjectMocks
    private OpenAiServiceImpl openAiServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openAiServiceImpl = new OpenAiServiceImpl(openAiService);
    }

    @Test
    void generateHeaders_validInput() {
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
    void generateHeaders_nullInput() {
        FormDataDTO formData = new FormDataDTO(null, "objection", "product");

        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.generateHeaders(formData));
    }

    @Test
    void generateHeaders_emptyInput() {
        FormDataDTO formData = new FormDataDTO("", "objection", "product");

        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.generateHeaders(formData));
    }

    @Test
    void generateHeadersFromPrompts_emptyPrompts() {
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.generateHeadersFromPrompts(Collections.emptyList()));
    }
}