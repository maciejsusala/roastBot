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
    void createPrompts() {
        String formField1 = "Zwiększenie sprzedaży";
        String formField2 = "brak zaufania";
        String formField3 = "nasz produkt";

        List<String> prompts = openAiServiceImpl.createPrompts(formField1, formField2, formField3);

        assertEquals(3, prompts.size());
        assertEquals("Stwórz propozycję nagłówka, który przyciągnie uwagę odbiorcy, obiecując pokonanie OBIEKCJI i osiągnięcie CELU za pomocą PRODUKTU. Nagłówek powinien mieć nie więcej niż 15 wyrazów. CEL = Zwiększenie sprzedaży OBIEKCJA = brak zaufania PRODUKT = nasz produkt", prompts.getFirst());
    }

    @Test
    void createPrompts_withNullFields() {
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts(null, "brak zaufania", "nasz produkt"));
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts("Zwiększenie sprzedaży", null, "nasz produkt"));
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts("Zwiększenie sprzedaży", "brak zaufania", null));
    }

    @Test
    void createPrompts_withEmptyFields() {
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts("", "brak zaufania", "nasz produkt"));
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts("Zwiększenie sprzedaży", "", "nasz produkt"));
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.createPrompts("Zwiększenie sprzedaży", "brak zaufania", ""));
    }



    @Test
    void generateHeaders_withValidPrompts() {
        String prompt = "Test prompt";
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(new ChatMessage("assistant", "Generated header"));
        ChatCompletionResult response = new ChatCompletionResult();
        response.setChoices(Collections.singletonList(choice));

        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(response);

        List<String> headers = openAiServiceImpl.generateHeaders(Collections.singletonList(prompt));

        assertEquals(1, headers.size());
        assertEquals("Generated header", headers.getFirst());
    }

    @Test
    void generateHeaders_withNullPrompts() {
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.generateHeaders(null));
    }

    @Test
    void generateHeaders_withEmptyPrompts() {
        assertThrows(IllegalArgumentException.class, () -> openAiServiceImpl.generateHeaders(Collections.emptyList()));
    }
}