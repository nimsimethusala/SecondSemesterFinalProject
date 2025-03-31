package lk.ijse.furnitureapp_back_end.controller;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.AuthDTO;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.dto.UserDTO;
import lk.ijse.furnitureapp_back_end.service.UserService;
import lk.ijse.furnitureapp_back_end.util.JwtUtil;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.updateUser(userDTO);
            return switch (res) {
                case VarList.OK -> ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", userDTO));
                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            };
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        try {
            int res = userService.deleteUser(id);
            return switch (res) {
                case VarList.OK -> ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Deleted Successfully", null));
                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            };
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User List Retrieved", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
