package pl.maciejsusala.aiheadergenerator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import pl.maciejsusala.aiheadergenerator.dto.UserDTO;
import pl.maciejsusala.aiheadergenerator.exception.DuplicateUserException;
import pl.maciejsusala.aiheadergenerator.exception.UserNotFoundException;
import pl.maciejsusala.aiheadergenerator.model.UserModel;
import pl.maciejsusala.aiheadergenerator.repository.UserRepository;
import pl.maciejsusala.aiheadergenerator.service.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        log.info("Adding new user with details {}", userDTO);
        UserModel user = objectMapper.convertValue(userDTO, UserModel.class);
        if (userRepository.existsByLogin(userDTO.login())) {
            throw new DuplicateUserException("Login already exists: " + userDTO.login());
        }
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new DuplicateUserException("Email already exists: " + userDTO.email());
        }
        UserModel savedUser = userRepository.save(user);
        return objectMapper.convertValue(savedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting star with id {}", id);
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " +id));
        userRepository.delete(user);
    }
}
