package pl.maciejsusala.roastbot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.maciejsusala.roastbot.dto.FormDataDTO;
import pl.maciejsusala.roastbot.dto.RoastResponseDTO;
import pl.maciejsusala.roastbot.service.RoastGenerationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/openai")
@AllArgsConstructor
public class OpenAiController {
    private final RoastGenerationService roastGenerationService;


    @PostMapping("/roast")
    @ResponseStatus(HttpStatus.OK)
    public RoastResponseDTO generateRoast(@Valid @RequestBody FormDataDTO formData) {
        return roastGenerationService.generateRoast(formData);
    }
}