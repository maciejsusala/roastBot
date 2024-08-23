package pl.maciejsusala.aiheadergenerator.controller;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.maciejsusala.aiheadergenerator.dto.UserDTO;
import pl.maciejsusala.aiheadergenerator.service.UserServiceInterface;

;

@RestController
@RequestMapping("/api/v1/openai/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @PostMapping("add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser (@RequestBody @Valid UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("delete-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
