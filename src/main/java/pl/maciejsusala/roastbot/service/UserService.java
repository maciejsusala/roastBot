package pl.maciejsusala.roastbot.service;

import pl.maciejsusala.roastbot.dto.UserDTO;

public interface UserService {


    UserDTO addUser(UserDTO userDTO);


    void deleteUser(Long id);
}
