package pl.maciejsusala.aiheadergenerator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

public record UserDTO(
    Long id,
    @Getter
    @NotBlank(message = "Name cannot be blank")
    String name,

    // Use char[] instead of String to prevent the password from being stored in the String pool
    @Getter
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    char[] password,

    @Getter
    String email
) implements Serializable {
}