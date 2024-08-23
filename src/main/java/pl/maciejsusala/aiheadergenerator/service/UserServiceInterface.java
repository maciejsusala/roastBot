package pl.maciejsusala.aiheadergenerator.service;

import pl.maciejsusala.aiheadergenerator.dto.UserDTO;

public interface UserServiceInterface {


    UserDTO addUser(UserDTO userDTO);


    void deleteUser(Long id);
}
