package lk.ijse.furnitureapp_back_end.controller;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.dto.UserDTO;
import lk.ijse.furnitureapp_back_end.service.AdminUserService;
import lk.ijse.furnitureapp_back_end.service.UserService;
import lk.ijse.furnitureapp_back_end.util.JwtUtil;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/adminuser")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("Admin user save = "+userDTO);
        int result = adminUserService.saveUser(userDTO);

        if (result == VarList.Created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } else if (result == VarList.Not_Acceptable) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user.");
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = adminUserService.updateUser(userDTO);
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        try {
            int res = adminUserService.deleteUser(id);
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
            List<UserDTO> users = adminUserService.getAllUsers();
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User List Retrieved", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
