package lk.ijse.furnitureapp_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemResponseDto {
    private UUID productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double price;
}
