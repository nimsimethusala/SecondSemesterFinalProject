package lk.ijse.furnitureapp_back_end.dto;

import jakarta.persistence.*;
import lk.ijse.furnitureapp_back_end.entity.CartItem;
import lk.ijse.furnitureapp_back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private UUID cartId;
    private User user;
}
