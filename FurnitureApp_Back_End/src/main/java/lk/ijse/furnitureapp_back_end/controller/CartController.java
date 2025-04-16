package lk.ijse.furnitureapp_back_end.controller;

import lk.ijse.furnitureapp_back_end.dto.CartDto;
import lk.ijse.furnitureapp_back_end.dto.CartSaveDto;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.service.CartService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveCart(@RequestBody CartSaveDto cartSaveDto) {
        try {
            CartDto savedCart = cartService.saveCart(cartSaveDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Cart saved successfully", savedCart));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to save cart", null));
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getCartByUser(@PathVariable UUID userId) {
        try {
            CartDto cart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Cart fetched successfully", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to fetch cart", null));
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> clearCart(@PathVariable UUID userId) {
        try {
            cartService.clearCartByUserId(userId);
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Cart cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to clear cart", null));
        }
    }
}