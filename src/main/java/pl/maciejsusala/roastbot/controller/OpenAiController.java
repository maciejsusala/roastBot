package pl.maciejsusala.roastbot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;
import pl.maciejsusala.roastbot.service.OpenAiServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/openai")
@AllArgsConstructor
public class OpenAiController {
    private final OpenAiServiceInterface openAiService;


    //TODO coś się stało z obsługą błędu strona nie istnieje wywala internala 500
    @PostMapping("/roast")
    public ResponseEntity<String> generateRoast(@Valid @RequestBody FormDataDTO formData) {
        RoastResponseDTO headerResponse = openAiService.generateRoast(formData);
        return ResponseEntity.ok(headerResponse.roast());
    }
}