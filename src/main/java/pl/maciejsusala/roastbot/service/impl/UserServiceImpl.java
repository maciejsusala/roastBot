package pl.maciejsusala.roastbot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.maciejsusala.roastbot.dto.UserDTO;
import pl.maciejsusala.roastbot.exception.DuplicateUserException;
import pl.maciejsusala.roastbot.exception.UserNotFoundException;
import pl.maciejsusala.roastbot.model.UserModel;
import pl.maciejsusala.roastbot.model.UserRole;
import pl.maciejsusala.roastbot.repository.UserRepository;
import pl.maciejsusala.roastbot.service.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO addUser(UserDTO userDTO) {
        log.info("Adding new user with details {}", userDTO);
        UserModel user = objectMapper.convertValue(userDTO, UserModel.class);
        if (userDTO.role() != null && !userDTO.role().equals(UserRole.USER) && !userDTO.role().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("Invalid role");
        }
        if (userRepository.existsByLogin(userDTO.login())) {
            throw new DuplicateUserException("Login already exists: " + userDTO.login());
        }
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new DuplicateUserException("Email already exists: " + userDTO.email());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserModel savedUser = userRepository.save(user);
        return objectMapper.convertValue(savedUser, UserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        log.info("Deleting star with id {}", id);
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " +id));
        userRepository.delete(user);
    }
}
