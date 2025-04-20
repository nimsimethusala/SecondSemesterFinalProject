package lk.ijse.furnitureapp_back_end.dto;

import lk.ijse.furnitureapp_back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
    private UUID id;
    private UUID productId;
    private int quantity;
    private double price;
}
