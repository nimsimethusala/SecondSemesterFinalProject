package lk.ijse.furnitureapp_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private UUID orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String userName;
}
