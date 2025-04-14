package lk.ijse.furnitureapp_back_end.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    private UUID userId;
    private UUID orderId;
    private PaymentDto payment;
    private double totalAmount;
    private List<OrderItemDto> items;
}
