package pl.maciejsusala.roastbot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;
import pl.maciejsusala.roastbot.service.OpenAiServiceInterface;

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
        when(openAiService.generateRoast(any(FormDataDTO.class)))
                .thenReturn(new RoastResponseDTO("You are roasted by AI"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void generateRoast_validInput() throws Exception {
        String formDataJson = """
                {
                    "formField1": "Problem",
                    "formField2": "Reason"
                }
                """;

        mockMvc.perform(post("/api/v1/openai/roast")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "roast": "You are roasted by AI"
                        }
                        """));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void generateRoast_missingRequiredField() throws Exception {
        String formDataJson = """
                {
                    "formField1": "Problem"
                }
                """;

        mockMvc.perform(post("/api/v1/openai/roast")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void generateRoast_emptyField() throws Exception {
        String formDataJson = """
                {
                    "formField1": "Problem",
                    "formField2": ""
                }
                """;

        mockMvc.perform(post("/api/v1/openai/roast")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void nonExistentEndpoint() throws Exception {
        mockMvc.perform(post("/api/v1/openai/nonexistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}