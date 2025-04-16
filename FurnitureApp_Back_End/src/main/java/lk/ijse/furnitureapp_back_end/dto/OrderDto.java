package lk.ijse.furnitureapp_back_end.dto;

import lk.ijse.furnitureapp_back_end.entity.Payment;
import lk.ijse.furnitureapp_back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private UUID orderId;
    private User user;
    private LocalDateTime orderDate;
    private double totalAmount;
    private Payment payment;
}