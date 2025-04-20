package lk.ijse.furnitureapp_back_end.service.impl;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import lk.ijse.furnitureapp_back_end.dto.*;
import lk.ijse.furnitureapp_back_end.entity.*;
import lk.ijse.furnitureapp_back_end.repo.*;
import lk.ijse.furnitureapp_back_end.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, PaymentRepository paymentRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        // Fetch user
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


//         Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(orderRequestDto.getTotalAmount());

        Order savedOrder = orderRepository.save(order);
        System.out.println(savedOrder.getOrderId());


        // Save order items
        for (OrderItemDto itemDto : orderRequestDto.getItems()) {
            OrderItem item = new OrderItem();

            item.setOrder(savedOrder);
            item.setPrice(itemDto.getPrice());
            item.setQuantity(itemDto.getQuantity());

            System.out.println(itemDto);

            Product product = productRepository.findById(itemDto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));
            item.setProduct(product);

            System.out.println("OrderItem: " + item);
            orderItemRepository.save(item);
        }

        //update products
        for (OrderItemDto itemDto : orderRequestDto.getItems()) {
            Product product = productRepository.findById(itemDto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));
            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
        }

//      Save payment first
        Payment payment = new Payment();
        payment.setAmount(orderRequestDto.getTotalAmount());
        payment.setOrder(savedOrder);
        Payment savedPayment = paymentRepository.save(payment);

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
                order.getTotalAmount()
        );
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();

        return orderList.stream().map(order ->
                new OrderResponseDto(order.getOrderId(), order.getOrderDate(), order.getTotalAmount(), order.getUser().getName())
        ).collect(Collectors.toList());
    }
}