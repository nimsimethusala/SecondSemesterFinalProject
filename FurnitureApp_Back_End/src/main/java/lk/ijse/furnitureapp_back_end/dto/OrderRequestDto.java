package lk.ijse.furnitureapp_back_end.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequestDto {
    private UUID userId;
    private UUID orderId;
    private double totalAmount;
    private List<OrderItemDto> items;
}
