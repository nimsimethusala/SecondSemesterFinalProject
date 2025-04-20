package lk.ijse.furnitureapp_back_end.service;


import lk.ijse.furnitureapp_back_end.dto.UserDTO;
import lk.ijse.furnitureapp_back_end.entity.User;

import java.util.List;


public interface UserService {
    int saveUser(UserDTO userDTO);
}