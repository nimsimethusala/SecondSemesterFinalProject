package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.entity.Order;
import lk.ijse.furnitureapp_back_end.entity.Payment;
import lk.ijse.furnitureapp_back_end.repo.OrderRepository;
import lk.ijse.furnitureapp_back_end.repo.PaymentRepository;
import lk.ijse.furnitureapp_back_end.service.paymentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class paymentServiceImpl implements paymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public paymentServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void savePayment(String orderId, String paymentId, double amount) {
        UUID uuid = UUID.fromString(orderId);
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setPaymentId(UUID.fromString(paymentId));
        payment.setOrder(order);
        payment.setAmount(amount);
        paymentRepository.save(payment);
    }

}
