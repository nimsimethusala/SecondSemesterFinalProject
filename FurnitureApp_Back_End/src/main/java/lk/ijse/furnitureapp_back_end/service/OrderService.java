package lk.ijse.furnitureapp_back_end.service;

import lk.ijse.furnitureapp_back_end.dto.OrderDto;
import lk.ijse.furnitureapp_back_end.dto.OrderRequestDto;
import lk.ijse.furnitureapp_back_end.dto.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto placeOrder(OrderRequestDto orderRequestDto);

    OrderDto getOrderById(UUID orderId);

    List<OrderResponseDto> getAllOrders();
}