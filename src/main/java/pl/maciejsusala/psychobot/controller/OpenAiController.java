package pl.maciejsusala.psychobot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejsusala.psychobot.dto.FormDataDTO;
import pl.maciejsusala.psychobot.dto.HeaderResponseDTO;
import pl.maciejsusala.psychobot.service.OpenAiServiceInterface;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/openai")
@AllArgsConstructor
public class OpenAiController {
    private final OpenAiServiceInterface openAiService;

    @PostMapping("/generate-header2")
    public ResponseEntity<List<String>> generateHeaders(@Valid @RequestBody FormDataDTO formData) {
        HeaderResponseDTO headerResponse = openAiService.generateHeaders(formData);
        return ResponseEntity.ok(headerResponse.headers());
    }
}