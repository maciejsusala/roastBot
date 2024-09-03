package pl.maciejsusala.roastbot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejsusala.roastbot.dto.UserDTO;
import pl.maciejsusala.roastbot.model.UserRole;
import pl.maciejsusala.roastbot.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void addUser_validInputWithRole() throws Exception {
        //when
        UserDTO userDTO = new UserDTO(1L, "JohnDoe", "password123", "john.doe@example.com", UserRole.ADMIN);
        when(userService.addUser(any(UserDTO.class))).thenReturn(userDTO);

        String userJson = """
            {
                "id": 1,
                "login": "JohnDoe",
                "password": "password123",
                "email": "john.doe@example.com",
                "role": "ADMIN"
            }
            """;

        mockMvc.perform(post("/api/v1/openai/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().json(userJson));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void addUser_invalidRole() throws Exception {
        //when
        String userJson = """
            {
                "id": 1,
                "login": "JohnDoe",
                "password": "password123",
                "email": "john.doe@example.com",
                "role": "INVALID_ROLE"
            }
            """;
        mockMvc.perform(post("/api/v1/openai/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void addUser_existingLogin() throws Exception {
        //when
        UserDTO userDTO = new UserDTO(1L, "JohnDoe", "password123", "john.doe@example.com", UserRole.USER);
        when(userService.addUser(any(UserDTO.class))).thenThrow(new IllegalArgumentException("Login already exists"));

        String userJson = """
            {
                "id": 1,
                "login": "JohnDoe",
                "password": "password123",
                "email": "john.doe@example.com",
                "role": "USER"
            }
            """;

        mockMvc.perform(post("/api/v1/openai/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "message": "Invalid input",
                        "details": "Login already exists"
                    }
                    """, false));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void addUser_existingEmail() throws Exception {
        //when
        UserDTO userDTO = new UserDTO(1L, "JohnDoe", "password123", "john.doe@example.com", UserRole.USER);
        when(userService.addUser(any(UserDTO.class))).thenThrow(new IllegalArgumentException("Email already exists"));

        String userJson = """
            {
                "id": 1,
                "login": "JohnDoe",
                "password": "password123",
                "email": "john.doe@example.com",
                "role": "USER"
            }
            """;

        mockMvc.perform(post("/api/v1/openai/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "message": "Invalid input",
                        "details": "Email already exists"
                    }
                    """, false));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteUser_validId() throws Exception {
        //when
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(post("/api/v1/openai/user/delete-user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(authorities = "USER")
    void addUser_asUser_shouldReturnForbidden() throws Exception {
        //when
        String userJson = """
            {
                "id": 1,
                "login": "JohnDoe",
                "password": "password123",
                "email": "john.doe@example.com",
                "role": "USER"
            }
            """;
        mockMvc.perform(post("/api/v1/openai/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //then
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(authorities = "USER")
    void deleteUser_asUser_shouldReturnForbidden() throws Exception {
        //when
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);


        mockMvc.perform(post("/api/v1/openai/user/delete-user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isForbidden());
    }
}