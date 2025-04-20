package lk.ijse.furnitureapp_back_end.service;

import lk.ijse.furnitureapp_back_end.dto.CartDto;
import lk.ijse.furnitureapp_back_end.dto.CartSaveDto;

import java.util.UUID;

public interface CartService {
    CartDto saveCart(CartSaveDto dto);

    CartDto getCartByUserId(UUID userId);

    void clearCartByUserId(UUID userId);
}