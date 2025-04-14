package lk.ijse.furnitureapp_back_end.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private UUID productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double price;
}
