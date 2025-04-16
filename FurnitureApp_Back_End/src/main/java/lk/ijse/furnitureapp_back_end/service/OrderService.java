package lk.ijse.furnitureapp_back_end.service;

import lk.ijse.furnitureapp_back_end.dto.OrderDto;
import lk.ijse.furnitureapp_back_end.dto.OrderRequestDto;

import java.util.UUID;

public interface OrderService {
    OrderDto placeOrder(OrderRequestDto orderRequestDto);

    OrderDto getOrderById(UUID orderId);
}