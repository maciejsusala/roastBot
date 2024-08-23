package pl.maciejsusala.aiheadergenerator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
    @Size(min = 6, message = "Email must be at least 3 characters long.")
    @Size(max = 50, message = "Email can't be longer than 50 characters")
    String email,

    @Getter
    String role
) implements Serializable {
}