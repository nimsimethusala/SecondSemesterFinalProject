package lk.ijse.furnitureapp_back_end.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.furnitureapp_back_end.dto.UserDTO;
import lk.ijse.furnitureapp_back_end.entity.User;
import lk.ijse.furnitureapp_back_end.repo.UserRepository;
import lk.ijse.furnitureapp_back_end.service.AdminUserService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole("ADMIN");
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public int updateUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            User user = userRepository.findByEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            user.setRole(userDTO.getRole());
            userRepository.save(user);
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int deleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
