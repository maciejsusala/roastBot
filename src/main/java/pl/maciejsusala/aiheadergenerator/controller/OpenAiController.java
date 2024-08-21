package pl.maciejsusala.aiheadergenerator.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejsusala.aiheadergenerator.dto.FormDataDTO;
import pl.maciejsusala.aiheadergenerator.dto.HeaderResponseDTO;
import pl.maciejsusala.aiheadergenerator.service.OpenAiServiceInterface;

import java.util.List;


@RestController
@RequestMapping("/api/v1/openai")
@AllArgsConstructor
public class OpenAiController {
    private final OpenAiServiceInterface openAiService;

    @GetMapping("/generate-header")
    public ResponseEntity<List<String>> generateHeaders(@RequestBody FormDataDTO formData) {
        HeaderResponseDTO headerResponse = openAiService.generateHeaders(formData);
        return ResponseEntity.ok(headerResponse.headers());
    }
}
