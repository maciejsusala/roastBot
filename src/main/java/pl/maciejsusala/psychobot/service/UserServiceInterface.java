package pl.maciejsusala.psychobot.service;

import pl.maciejsusala.psychobot.dto.UserDTO;

public interface UserServiceInterface {


    UserDTO addUser(UserDTO userDTO);


    void deleteUser(Long id);
}
