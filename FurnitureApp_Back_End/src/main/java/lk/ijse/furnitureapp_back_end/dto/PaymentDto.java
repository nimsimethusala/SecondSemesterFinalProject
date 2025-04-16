package lk.ijse.furnitureapp_back_end.dto;

import lk.ijse.furnitureapp_back_end.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private UUID paymentId;
    private double amount;
    private String paymentRef;
    private String status;
    private Order order;
}
