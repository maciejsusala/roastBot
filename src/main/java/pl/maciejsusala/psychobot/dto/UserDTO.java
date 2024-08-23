package pl.maciejsusala.psychobot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

public record UserDTO(
        Long id,
        @Getter
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 6, message = "Login must be at least 6 characters long.")
        @Size(max = 50, message = "Login can't be longer than 50 characters")
        String login,

        @Getter
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long.")
        @Size(max = 50, message = "Password can't be longer than 50 characters")
        String password,

        @Getter
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        @Size(min = 3, message = "Email must be at least 3 characters long.")
        @Size(max = 50, message = "Email can't be longer than 50 characters")
        String email,

        @Getter
        String role
) implements Serializable {
}