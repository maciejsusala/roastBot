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
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoastGenerationServiceImplTest {

    @Mock
    private OpenAiService openAiService;
    private RoastGenerationServiceImpl openAiServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        openAiServiceImpl = new RoastGenerationServiceImpl(openAiService);
    }

    @Test
    void generateRoast_validInput() {
        //when
        FormDataDTO formData = new FormDataDTO("Problem", "Reason");
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated Roast"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice));

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);

        RoastResponseDTO roastResponse = openAiServiceImpl.generateRoast(formData);

        //then
        assertEquals("Generated Roast", roastResponse.roast());
    }

    @Test
    void generateRoastFromPrompt_validInput() {
        //when
        String prompt = "prompt";
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated Roast"));
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(List.of(choice));

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);

        String roast = openAiServiceImpl.generateRoastFromPrompt(prompt);

        //then
        assertEquals("Generated Roast", roast);
    }
}