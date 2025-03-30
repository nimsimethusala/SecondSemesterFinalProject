package lk.ijse.furnitureapp_back_end.service;


import lk.ijse.furnitureapp_back_end.dto.UserDTO;

import java.util.List;


public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    int updateUser(UserDTO userDTO);
    int deleteUser(String email);

    List<UserDTO> getAllUsers();

}