package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO payment (payment_id, amount, payhere_ref, status) VALUES (:paymentId, :amount, :payhereRef, :status)", nativeQuery = true)
    void insertPaymentNative(
            @Param("paymentId") UUID paymentId,
            @Param("amount") double amount,
            @Param("payhereRef") String payhereRef,
            @Param("status") String status
    );
    @Modifying
    @Transactional
    @Query(value = "UPDATE payment SET order_id = :orderId WHERE payment_id = :paymentId", nativeQuery = true)
    void updateOrderIdInPayment(
            @Param("orderId") UUID orderId,
            @Param("paymentId") UUID paymentId
    );
}
