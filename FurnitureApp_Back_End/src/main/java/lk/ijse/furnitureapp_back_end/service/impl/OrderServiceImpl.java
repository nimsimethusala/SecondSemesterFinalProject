package lk.ijse.furnitureapp_back_end.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.furnitureapp_back_end.dto.OrderDto;
import lk.ijse.furnitureapp_back_end.dto.OrderItemDto;
import lk.ijse.furnitureapp_back_end.dto.OrderRequestDto;
import lk.ijse.furnitureapp_back_end.entity.Order;
import lk.ijse.furnitureapp_back_end.entity.OrderItem;
import lk.ijse.furnitureapp_back_end.entity.Payment;
import lk.ijse.furnitureapp_back_end.entity.User;
import lk.ijse.furnitureapp_back_end.repo.OrderItemRepository;
import lk.ijse.furnitureapp_back_end.repo.OrderRepository;
import lk.ijse.furnitureapp_back_end.repo.PaymentRepository;
import lk.ijse.furnitureapp_back_end.repo.UserRepository;
import lk.ijse.furnitureapp_back_end.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            PaymentRepository paymentRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        // Fetch user
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save payment first
        Payment savedPayment = paymentRepository.save(new Payment(
                orderRequestDto.getPayment().getPaymentId(),
                orderRequestDto.getPayment().getOrder(),
                orderRequestDto.getPayment().getAmount()
        ));

        // Create order
        Order order = new Order();
        order.setOrderId(orderRequestDto.getOrderId() != null ? orderRequestDto.getOrderId() : UUID.randomUUID());
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setPayment(savedPayment);

        Order savedOrder = orderRepository.save(order);

        // Save order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : orderRequestDto.getItems()) {
            OrderItem item = new OrderItem();
            item.setId(itemDto.getId() != null ? itemDto.getId() : UUID.randomUUID());
            item.setOrder(savedOrder);
            item.setPrice(itemDto.getPrice());
            item.setQuantity(itemDto.getQuantity());
            orderItems.add(item);
        }
        orderItemRepository.saveAll(orderItems);

        // Build response DTO
        OrderDto response = new OrderDto();
        response.setOrderId(savedOrder.getOrderId());
        response.setUser(user);
        response.setOrderDate(savedOrder.getOrderDate());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setPayment(savedPayment);
        return response;
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderDto(
                order.getOrderId(),
                order.getUser(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getPayment()
        );
    }
}