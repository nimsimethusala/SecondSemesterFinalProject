package lk.ijse.furnitureapp_back_end.dto;

import jakarta.persistence.*;
import lk.ijse.furnitureapp_back_end.entity.Cart;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private UUID id;
    private Cart cart;
    private Product product;
    private int quantity;
}
