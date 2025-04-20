package lk.ijse.furnitureapp_back_end.service;

import lk.ijse.furnitureapp_back_end.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AdminUserService {

    int saveUser(UserDTO userDTO);

    int updateUser(UserDTO userDTO);

    int deleteUser(String email);

    List<UserDTO> getAllUsers();
}
