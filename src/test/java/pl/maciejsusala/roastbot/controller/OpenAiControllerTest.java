package pl.maciejsusala.roastbot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;
import pl.maciejsusala.roastbot.service.OpenAiServiceInterface;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenAiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenAiServiceInterface openAiService;

    @BeforeEach
    void setUp() {
        when(openAiService.generateHeaders(any(FormDataDTO.class)))
                .thenReturn(new RoastResponseDTO(List.of("Generated Header 1", "Generated Header 2", "Generated Header 3")));
    }

    @Test
    void generateRoast_validInput() throws Exception {
        String formDataJson = """
                {
                    "formField1": "goal",
                    "formField2": "objection",
                }
                """;

        mockMvc.perform(post("/api/v1/openai/generate-header2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        ["Generated Header 1", "Generated Header 2", "Generated Header 3"]
                        """));
    }

    @Test
    void generateRoast_missingRequiredField() throws Exception {
        String formDataJson = """
                {
                    "formField1": "goal",
                    "formField2": "objection"
                }
                """;

        mockMvc.perform(post("/api/v1/openai/generate-header2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generateRoast_emptyField() throws Exception {
        String formDataJson = """
                {
                    "formField1": "goal",
                    "formField2": "",
                    "formField3": "product"
                }
                """;

        mockMvc.perform(post("/api/v1/openai/generate-header2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isBadRequest());
    }
}